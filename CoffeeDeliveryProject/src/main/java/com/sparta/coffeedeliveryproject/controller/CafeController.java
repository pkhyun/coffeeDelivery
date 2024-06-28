package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.CafeMenuListResponseDto;
import com.sparta.coffeedeliveryproject.dto.CafeResponseDto;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.CafeService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<CafeResponseDto>> getAllCafe(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "sortBy", defaultValue = "cafeId") String sortBy) {

        List<CafeResponseDto> responseDtoList = cafeService.getAllCafe(page - 1, sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeMenuListResponseDto> getCafeWithMenuList(
        @PathVariable(value = "cafeId") Long cafeId) {

        CafeMenuListResponseDto responseDto = cafeService.getCafeWithMenuList(cafeId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/users/likes")
    public ResponseEntity<List<CafeResponseDto>> getFavoriteCafe(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<CafeResponseDto> responseDtoList = cafeService.getFavoriteCafe(page - 1, sortBy, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

}
