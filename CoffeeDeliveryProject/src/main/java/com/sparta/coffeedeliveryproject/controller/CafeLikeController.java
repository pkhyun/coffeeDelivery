package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.service.CafeLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cafes")
@RequiredArgsConstructor
public class CafeLikeController {

    private final CafeLikeService cafeLikeService;

    //카페 좋아요
    @PostMapping("/{cafeId}/likes")
    public ResponseEntity<MessageResponseDto> likeCafe(@PathVariable Long cafeId,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDto responseDto = cafeLikeService.likeCafe(cafeId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
