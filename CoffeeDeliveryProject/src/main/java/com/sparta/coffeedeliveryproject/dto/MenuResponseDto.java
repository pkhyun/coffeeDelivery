package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.Menu;
import com.sparta.coffeedeliveryproject.entity.MenuType;
import lombok.Getter;

@Getter
public class MenuResponseDto {

    private Long menuId;

    private String menuName;

    private Long menuPrice;

    private Long cafeId;

    private MenuType menuType;

    public MenuResponseDto(Menu menu) {
        this.menuId = menu.getMenuId();
        this.menuName = menu.getMenuName();
        this.menuPrice = menu.getMenuPrice();
        this.cafeId = menu.getCafe().getCafeId();
        this.menuType = menu.getMenuType();
    }

}
