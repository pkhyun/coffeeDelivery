package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.MenuType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MenuRequestDto {

    @NotBlank(message = "[menuName:blank] 메뉴 이름을 입력해주세요!")
    private String menuName;

    @NotNull(message = "[menuPrice:null] 메뉴 가격을 입력해주세요!")
    private Long menuPrice;

    @NotNull(message = "[menuType:blank] 메뉴 타입을 입력해주세요!")
    private String menuType;

}
