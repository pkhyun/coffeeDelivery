package com.sparta.coffeedeliveryproject.entity;

import lombok.Getter;

@Getter
public enum MenuType {
    COFFEE("커피"),
    ADE("에이드"),
    JUICE("주스"),
    DESSERT("디저트");

    private final String menuType;

    MenuType(String menuType) {
        this.menuType = menuType;
    }

}