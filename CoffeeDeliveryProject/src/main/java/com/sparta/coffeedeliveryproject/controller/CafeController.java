package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.CafeMenuListResponseDto;
import com.sparta.coffeedeliveryproject.dto.CafeResponseDto;
import com.sparta.coffeedeliveryproject.dto.MenuDto;
import com.sparta.coffeedeliveryproject.dto.MenuSearchCond;
import com.sparta.coffeedeliveryproject.entity.MenuType;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cafes")
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    @GetMapping
    public ResponseEntity<Page<CafeResponseDto>> getAllCafe(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "sortBy", defaultValue = "cafeId") String sortBy) {

        Page<CafeResponseDto> responseDtoList = cafeService.getAllCafe(page - 1, sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeMenuListResponseDto> getCafeWithMenuList(
        @PathVariable(value = "cafeId") Long cafeId) {

        CafeMenuListResponseDto responseDto = cafeService.getCafeWithMenuList(cafeId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    //팔로워 게시글 목록 조회기능 추가 (본인이 좋아요한 카페의 메뉴 조회)
    @GetMapping("/users/favorite")
    public ResponseEntity<Page<MenuDto>> getUserFavoriteCafe(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "menuType", required = false) MenuType menuType,
        @RequestParam(value = "minPrice", required = false) Long minPrice,
        @RequestParam(value = "maxPrice", required = false) Long maxPrice,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        MenuSearchCond searchCond = new MenuSearchCond(menuType, minPrice, maxPrice);

        Page<MenuDto> responseDtoList = cafeService.getUserFavoriteCafe(page - 1, userDetails.getUser(), searchCond);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    //팔로워 TOP 10 목록 조회기능 추가 (좋아요 많은순 TOP10 카페 조회)
    @GetMapping("/favorite")
    public ResponseEntity<Page<CafeResponseDto>> getFavoriteCafe(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        Page<CafeResponseDto> responseDtoList = cafeService.getFavoriteCafe();

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

}
