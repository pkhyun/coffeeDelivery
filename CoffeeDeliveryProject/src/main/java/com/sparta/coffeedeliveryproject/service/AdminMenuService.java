package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.MenuRequestDto;
import com.sparta.coffeedeliveryproject.dto.MenuResponseDto;
import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.Menu;
import com.sparta.coffeedeliveryproject.entity.MenuType;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.entity.UserRole;
import com.sparta.coffeedeliveryproject.repository.CafeRepository;
import com.sparta.coffeedeliveryproject.repository.MenuRepository;
import com.sparta.coffeedeliveryproject.repository.UserRepository;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminMenuService {

    private final MenuRepository menuRepository;
    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;

    public MenuResponseDto createMenu(Long cafeId, MenuRequestDto requestDto) {

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(
                () -> new IllegalArgumentException("해당 카페를 찾을 수 없습니다.")
        );

        String menuName = requestDto.getMenuName();
        Long menuPrice = requestDto.getMenuPrice();
        MenuType menuType;

        // menuType 유효성 검증
        try {
            menuType = MenuType.valueOf(requestDto.getMenuType().toUpperCase()); // 대소문자 구분 없이 처리
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 메뉴 타입입니다.");
        }

        Menu menu = new Menu(menuName, menuPrice, cafe, menuType);
        cafe.addMenuList(menu);

        Menu saveMenu = menuRepository.save(menu);

        return new MenuResponseDto(saveMenu);
    }

    public MessageResponseDto deleteMenu(Long menuId) {

        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다.")
        );

        menuRepository.delete(menu);

        return new MessageResponseDto("삭제 완료 되었습니다.");
    }

    @Transactional
    public boolean isAdmin(UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getUserId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다."));

        for (UserRole role : user.getUserRoles()) {
            if ("ADMIN".equals(role.getRole())) {
                return true;
            }
        }
        return false;
    }

}
