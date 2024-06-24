package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.*;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.entity.UserRole;
import com.sparta.coffeedeliveryproject.enums.UserStatusEnum;
import com.sparta.coffeedeliveryproject.exceptions.PasswordMismatchException;
import com.sparta.coffeedeliveryproject.exceptions.RecentlyUsedPasswordException;
import com.sparta.coffeedeliveryproject.jwt.JwtUtil;
import com.sparta.coffeedeliveryproject.repository.UserRepository;
import com.sparta.coffeedeliveryproject.repository.UserRoleRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Value("${ADMIN_TOKEN}")
    String adminToken;

    public MessageResponseDto signup(SignupRequestDto signupRequestDto) {

        String nickName = signupRequestDto.getNickName();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        signupRequestDto.setPassword(password);

        // 회원 중복 확인
        Optional<User> checkNickName = userRepository.findByNickName(nickName);
        if (checkNickName.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        User user = new User(signupRequestDto);

        Set<UserRole> userRoles = new HashSet<>();

        if (!signupRequestDto.getRole().isEmpty()) {
            if (signupRequestDto.getRole().equals("ADMIN")) {
                if (signupRequestDto.getAdminToken() != null && signupRequestDto.getAdminToken().equals(adminToken)) {
                    UserRole userRole = findRole(signupRequestDto.getRole());
                    userRoles.add(userRole);
                } else {
                    throw new IllegalArgumentException("adminToken이 올바르지 않습니다.");
                }
            } else if (signupRequestDto.getRole().equals("USER")) {
                UserRole userRole = findRole("USER");
                userRoles.add(userRole);
            } else {
                throw new IllegalArgumentException("올바르지 않은 role값 입니다.");
            }
        } else {
            UserRole userRole = findRole("USER");
            userRoles.add(userRole);
        }

        user.setUserRoles(userRoles);
        user.getPastPasswords().add(password);


        userRepository.save(user);

        return new MessageResponseDto("회원가입이 완료되었습니다.");
    }

    public MessageResponseDto login(LogingRequestDto logingRequestDto, HttpServletResponse response) {

        // 사용자의 아이디, 비밀번호를 받아온다.
        String userName = logingRequestDto.getUserName();
        String password = logingRequestDto.getPassword();

        // 회원가입이 된 사용자인지 확인.
        User user = findUserByName(userName);

        // 회원가입 시 입력한 비밀번호와 로그인시 입력한 비밀번호가 일치하지 않을 경우
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 액세스 토큰, 리프레쉬 토큰 생성
        String accessToken = jwtUtil.createAccessToken(user.getUserName(), UserStatusEnum.ACTIVE);
        String refreshToken = jwtUtil.createRefreshToken(user.getUserName(), UserStatusEnum.ACTIVE);

        // header에 토큰 저장
        jwtUtil.addJwtToHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken, response);
        jwtUtil.addJwtToHeader(JwtUtil.REFRESH_HEADER, refreshToken, response);

        // DB에 리프레쉬토큰 저장
        String substringRefreshToken = refreshToken.substring(JwtUtil.BEARER_PREFIX.length());
        user.setRefreshToken(substringRefreshToken);
        userRepository.save(user);

        return new MessageResponseDto("로그인이 완료되었습니다.");
    }

    public MessageResponseDto reissueToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = jwtUtil.getTokenFromHeader(JwtUtil.REFRESH_HEADER, request);

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new IllegalArgumentException("리프레시 토큰도 만료되었습니다. 다시 로그인 해주세요.");
        }

        Claims info = jwtUtil.getUserInfoFromToken(refreshToken);
        String userName = info.getSubject();

        User user = findUserByName(userName);

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new IllegalArgumentException("해당 유저의 리프레시 토큰이 일치하지 않습니다.");
        }

        String newAccessToken = jwtUtil.createAccessToken(user.getUserName(), user.getUserStatus());
        String newRefreshToken = jwtUtil.createRefreshToken(user.getUserName(), user.getUserStatus());

        jwtUtil.addJwtToHeader(JwtUtil.AUTHORIZATION_HEADER, newAccessToken, response);
        jwtUtil.addJwtToHeader(JwtUtil.REFRESH_HEADER, newRefreshToken, response);

        String substringRefreshToken = newRefreshToken.substring(JwtUtil.BEARER_PREFIX.length());
        user.setRefreshToken(substringRefreshToken);
        userRepository.save(user);

        return new MessageResponseDto("토큰 재발급이 완료되었습니다.");
    }

    public UserProfileEditResponseDto editProfile(UserProfileEditRequestDto userProfileEditRequestDto, Long userId) {
        User user = findUserById(userId);

        if (userProfileEditRequestDto.getNewNickName() != null) {
            user.editUserNickName(userProfileEditRequestDto.getNewNickName());
        }

        // 현재 비밀번호 확인
        if (userProfileEditRequestDto.getPassword() != null) {
            // TODO: 나중에 password 암호화되면 encoder 추가 필요
            // 일치하지 않으면
            if (!userProfileEditRequestDto.getPassword().matches(user.getPassword())) {
                throw new PasswordMismatchException("현재 비밀번호가 일치하지 않습니다.");
            }

            // 이전 비밀번호에 새로운 비밀번호가 있으면
            if (user.getPastPasswords().contains(userProfileEditRequestDto.getNewPassword())) {
                throw new RecentlyUsedPasswordException("최근 설정한 4개의 비밀번호로는 비밀번호를 변경하실 수 없습니다.");
            }

            // 새로운 비밀번호 설정
            user.editUserPassword(userProfileEditRequestDto.getNewPassword());

            // 과거 비밀번호 저장하는 리스트의 갯수가 4개인지 확인
            if (user.getPastPasswords().stream().count() == 4) {

                // 가장 먼저 저장된 password 삭제
                user.getPastPasswords().remove(0);

                // 새로운 비밀번호 저장
                user.getPastPasswords().add(userProfileEditRequestDto.getNewPassword());

            } else {
                user.getPastPasswords().add(userProfileEditRequestDto.getNewPassword());
            }
        }

        return new UserProfileEditResponseDto(user);

    }

    public MessageResponseDto logout(Long userId) {
        User user = findUserById(userId);

        // 리프래시 토큰 초기화
        user.editRefreshToken(null);

        // 사용자 정보 저장
        userRepository.save(user);

        // SecurityContextHolder 초기화
        SecurityContextHolder.clearContext();

        return new MessageResponseDto("로그아웃이 완료되었습니다.");
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 프로필을 찾을 수 없습니다."));
    }

    private UserRole findRole(String role) {
        return userRoleRepository.findByRole(role)
                .orElseThrow(() -> new IllegalArgumentException("사용자의 권한을 찾을 수 없습니다."));
    }

    private User findUserByName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
    }

}