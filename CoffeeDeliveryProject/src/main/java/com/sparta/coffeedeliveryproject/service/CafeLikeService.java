package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.CafeLike;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.repository.CafeLikeRepository;
import com.sparta.coffeedeliveryproject.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CafeLikeService {

    private final CafeLikeRepository cafeLikeRepository;
    private final CafeRepository cafeRepository;

    //카페 좋아요
    @Transactional
    public MessageResponseDto likeCafe(Long cafeId, User user) {
        Cafe cafe = findCafeById(cafeId);

        if (cafeLikeRepository.findByCafeCafeIdAndUserUserId(cafeId, user.getUserId()).isPresent()) {
            throw new IllegalArgumentException("중복 좋아요는 할 수 없습니다.");
        } else {
            cafe.likeCafe();
            cafeLikeRepository.save(new CafeLike(user, cafe));
        }
        return new MessageResponseDto("좋아요 성공");
    }

    //카페 좋아요 취소
    @Transactional
    public MessageResponseDto unlikeCafe(Long cafeId, User user) {
        Cafe cafe = findCafeById(cafeId);

        CafeLike cafeLike = cafeLikeRepository.findByCafeCafeIdAndUserUserId(cafeId, user.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("해당 카페에 좋아요를 하지 않았습니다."));
        cafe.unlikeCafe();
        cafeLikeRepository.delete(cafeLike);
        return new MessageResponseDto("좋아요 취소");
    }


    //id로 카페 찾기
    private Cafe findCafeById(Long cafeId) {
        return cafeRepository.findById(cafeId).orElseThrow(() ->
                new IllegalArgumentException("해당 카페를 찾을 수 없습니다."));
    }

}
