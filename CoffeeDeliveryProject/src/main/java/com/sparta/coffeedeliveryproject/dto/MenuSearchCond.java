package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.MenuType;
import lombok.Getter;

@Getter
public class MenuSearchCond {

    private MenuType menuType; // 메뉴 타입
    private Long minPrice;     // 최소 가격
    private Long maxPrice;     // 최대 가격

    public MenuSearchCond(MenuType menuType, Long minPrice, Long maxPrice) {
        this.menuType = menuType;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
