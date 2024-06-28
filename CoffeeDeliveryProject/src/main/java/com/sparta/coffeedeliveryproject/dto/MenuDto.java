package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.Menu;
import lombok.Getter;

@Getter
public class MenuDto {

    private Long menuId;
    private String menuName;
    private Long menuPrice;
    private String cafeName;

    public MenuDto(Menu menu) {
        this.menuId = menu.getMenuId();
        this.menuName = menu.getMenuName();
        this.menuPrice = menu.getMenuPrice();
        this.cafeName = menu.getCafe().getCafeName();
    }

}
