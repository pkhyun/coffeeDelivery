package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.dto.UserEditRequestDto;
import com.sparta.coffeedeliveryproject.dto.UserResponseDto;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.entity.UserRole;
import com.sparta.coffeedeliveryproject.enums.UserStatusEnum;
import com.sparta.coffeedeliveryproject.exceptions.PasswordMismatchException;
import com.sparta.coffeedeliveryproject.exceptions.RecentlyUsedPasswordException;
import com.sparta.coffeedeliveryproject.exceptions.UserNotFoundException;
import com.sparta.coffeedeliveryproject.repository.UserRepository;
import com.sparta.coffeedeliveryproject.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDto> getAllUsers(Long adminUserId) {
        User user = findUserById(adminUserId);

        checkRole(user);

        List<User> users = userRepository.findAll();

        // 모든 유저를 UserResponseDto로 매핑하여 list로 들어 반환
        return users.stream()
                .map(u -> new UserResponseDto(u))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto editUser(Long userId, UserEditRequestDto userEditRequestDto, Long adminUserId) {

        User user = findUserById(userId);
        User adminUser = findUserById(adminUserId);

        checkRole(adminUser);

        if (userEditRequestDto.getNewNickName() != null) {
            user.editUserNickName(userEditRequestDto.getNewNickName());
        }

        // 현재 비밀번호 확인
        if (userEditRequestDto.getPassword() != null) {
            // TODO: 나중에 password 암호화되면 encoder 추가 필요
            // 일치하지 않으면
            if (!passwordEncoder.matches(userEditRequestDto.getPassword(), user.getPassword())) {
                throw new PasswordMismatchException("현재 비밀번호가 일치하지 않습니다.");
            }

            // 이전 비밀번호에 새로운 비밀번호가 있으면
            for (String s : user.getPastPasswords()) {
                if (passwordEncoder.matches(userEditRequestDto.getNewPassword(), s)) {
                    throw new RecentlyUsedPasswordException("최근 설정한 4개의 비밀번호로는 비밀번호를 변경하실 수 없습니다.");
                }
            }

            // 새로운 비밀번호 설정
            user.editUserPassword(passwordEncoder.encode(userEditRequestDto.getNewPassword()));

            // 과거 비밀번호 저장하는 리스트의 갯수가 4개인지 확인
            if (user.getPastPasswords().size() >= 3) {

                // 가장 먼저 저장된 password 삭제
                user.getPastPasswords().remove(0);

                // 새로운 비밀번호 저장
                user.getPastPasswords().add(passwordEncoder.encode(userEditRequestDto.getNewPassword()));

            } else {
                user.getPastPasswords().add(passwordEncoder.encode(userEditRequestDto.getNewPassword()));
            }
        }


        return new UserResponseDto(user);
    }

    @Transactional
    public MessageResponseDto deleteUser(Long userId, Long adminUserId) {
        User user = findUserById(userId);
        User adminUser = findUserById(adminUserId);

        checkRole(adminUser);

        userRepository.delete(user);

        return new MessageResponseDto("유저가 삭제되었습니다.");
    }

    @Transactional
    public UserResponseDto changeUserRoleToAdmin(Long userId, Long adminUserId) {
        User user = findUserById(userId);
        User adminUser = findUserById(adminUserId);

        checkRole(adminUser);

        UserRole adminRole = userRoleRepository.findByRole("ADMIN")
                .orElseThrow(() -> new IllegalArgumentException("ADMIN 이라는 권한을 찾지 못하였습니다."));

        if (user.getUserRoles().contains(adminRole)) {
            throw new IllegalArgumentException("이미 관리자 권환을 가지고 있습니다.");
        }

        user.addUserRoles(adminRole);
        userRepository.save(user);

        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto userBlock(Long userId, Long adminUserId) {
        User user = findUserById(userId);
        User adminUser = findUserById(adminUserId);

        checkRole(adminUser);

        if (user.getUserStatus() == UserStatusEnum.BLOCK) {
            throw new IllegalArgumentException("이미 차단된 회원입니다.");
        }

        user.setUserStatus(UserStatusEnum.BLOCK);

        userRepository.save(user);

        return new UserResponseDto(user);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("현재 userId로 유저를 찾을 수 없습니다."));
    }

    private void checkRole(User user) {
        UserRole adminRole = userRoleRepository.findByRole("ADMIN")
                .orElseThrow(() -> new IllegalArgumentException("ADMIN 이라는 권한을 찾지 못하였습니다."));

        Set<UserRole> userRoles = user.getUserRoles();
        if (!userRoles.contains(adminRole)) {
            throw new IllegalArgumentException("관리자만 사용할 수 있는 기능입니다.");
        }
    }

}
