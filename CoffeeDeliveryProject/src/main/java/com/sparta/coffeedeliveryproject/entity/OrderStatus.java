package com.sparta.coffeedeliveryproject.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

    DELIVERY_START("배달중"),
    DELIVERY_COMPLETE("배달완료"),
    DELIVERY_CANCEL("배달취소");

    private final String orderStatus;

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
