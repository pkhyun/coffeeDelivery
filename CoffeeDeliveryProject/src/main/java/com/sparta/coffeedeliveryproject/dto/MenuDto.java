package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.Menu;
import com.sparta.coffeedeliveryproject.entity.MenuType;
import lombok.Getter;

@Getter
public class MenuDto {

    private Long menuId;
    private String menuName;
    private Long menuPrice;
    private String cafeName;
    private MenuType menuType;

    public MenuDto(Menu menu) {
        this.menuId = menu.getMenuId();
        this.menuName = menu.getMenuName();
        this.menuPrice = menu.getMenuPrice();
        this.cafeName = menu.getCafe().getCafeName();
        this.menuType = menu.getMenuType();
    }

    public MenuDto(Long menuId, String menuName, Long menuPrice, String cafeName, MenuType menuType) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.cafeName = cafeName;
        this.menuType = menuType;
    }

}
