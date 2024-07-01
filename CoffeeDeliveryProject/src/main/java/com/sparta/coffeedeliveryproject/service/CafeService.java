package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.*;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;

    public Page<CafeResponseDto> getAllCafe(int page, String sortBy) {

        Sort sort;

        if (sortBy.equals("cafeId")) {
            sort = Sort.by(Sort.Direction.ASC, sortBy);
        } else {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        }

        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<CafeResponseDto> CafePage = cafeRepository.findAll(pageable).map(CafeResponseDto::new);
        if (CafePage.isEmpty()) {
            throw new IllegalArgumentException("작성된 카페 페이지가 없거나, 입력된 " + (page + 1) + " 페이지에 글이 없습니다.");
        }

        return CafePage;
    }

    public CafeMenuListResponseDto getCafeWithMenuList(Long cafeId) {

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(
                () -> new IllegalArgumentException("해당 카페 페이지를 찾을 수 없습니다.")
        );

        List<MenuResponseDto> menuList = cafe.getMenuList().stream().map(MenuResponseDto::new).toList();

        return new CafeMenuListResponseDto(cafe, menuList);
    }

    public Page<MenuDto> getUserFavoriteCafe(int page, User user, MenuSearchCond searchCond) {

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Direction.DESC, "createdAt"));

        Page<MenuDto> cafePage = cafeRepository.findFavoriteCafesByUserIdWithSearch(
                user.getUserId(),
                searchCond.getMenuType(),
                searchCond.getMinPrice(),
                searchCond.getMaxPrice(),
                pageable);

        if (cafePage.isEmpty()) {
            throw new IllegalArgumentException("작성된 카페 페이지가 없거나, " + (page + 1) + " 페이지에 글이 없습니다.");
        }

        return cafePage;
    }

    public Page<CafeResponseDto> getFavoriteCafe() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.DESC, "cafeLikeCount"));
        Page<CafeResponseDto> cafePage = cafeRepository.findAll(pageable).map(CafeResponseDto::new);

        if (cafePage.isEmpty()) {
            throw new IllegalArgumentException("작성된 카페 페이지가 없습니다.");
        }

        return cafePage;
    }

}
