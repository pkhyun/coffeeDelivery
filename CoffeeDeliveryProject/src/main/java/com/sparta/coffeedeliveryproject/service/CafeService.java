package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.CafeMenuListResponseDto;
import com.sparta.coffeedeliveryproject.dto.CafeResponseDto;
import com.sparta.coffeedeliveryproject.dto.MenuDto;
import com.sparta.coffeedeliveryproject.dto.MenuResponseDto;
import com.sparta.coffeedeliveryproject.dto.MenuSearchCond;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.repository.CafeLikeRepository;
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

    public List<CafeResponseDto> getAllCafe(int page, String sortBy) {

        Sort sort;

        if (sortBy.equals("cafeId")) {
            sort = Sort.by(Sort.Direction.ASC, sortBy);
        } else {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        }

        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<CafeResponseDto> CafePage = cafeRepository.findAll(pageable).map(CafeResponseDto::new);
        List<CafeResponseDto> responseDtoList = CafePage.getContent();

        if (responseDtoList.isEmpty()) {
            throw new IllegalArgumentException("작성된 카페 페이지가 없거나, 입력된 " + (page + 1) + " 페이지에 글이 없습니다.");
        }

        return responseDtoList;
    }

    public CafeMenuListResponseDto getCafeWithMenuList(Long cafeId) {

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(
                () -> new IllegalArgumentException("해당 카페 페이지를 찾을 수 없습니다.")
        );

        List<MenuResponseDto> menuList = cafe.getMenuList().stream().map(MenuResponseDto::new).toList();

        return new CafeMenuListResponseDto(cafe, menuList);
    }

    public List<MenuDto> getUserFavoriteCafe(int page, User user/*, MenuSearchCond searchCond*/) {

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Direction.DESC, "createdAt"));
        Page<MenuDto> cafePage = cafeRepository.findFavoriteCafesByUserId(user.getUserId(), pageable).map(MenuDto::new);
        // 필터 조건에 따라 쿼리를 다르게 구성
//        Page<MenuDto> cafePage;
//        if (searchCond != null && (searchCond.getMenuType() != null || searchCond.getMinPrice() != null || searchCond.getMaxPrice() != null)) {
//            cafePage = cafeRepository.findFavoriteCafesByUserIdWithFilters(user.getUserId(), searchCond.getMenuType(), searchCond.getMinPrice(), searchCond.getMaxPrice(), pageable)
//                .map(MenuDto::new);
//        } else {
//            cafePage = cafeRepository.findFavoriteCafesByUserId(user.getUserId(), pageable)
//                .map(MenuDto::new);
//        }

        List<MenuDto> responseDtoList = cafePage.getContent();

        if (responseDtoList.isEmpty()) {
            throw new IllegalArgumentException("작성된 카페 페이지가 없거나, " + (page + 1) + " 페이지에 글이 없습니다.");
        }

        return responseDtoList;
    }

    public List<CafeResponseDto> getFavoriteCafe() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.DESC, "cafeLikeCount"));
        Page<CafeResponseDto> cafePage = cafeRepository.findAll(pageable).map(CafeResponseDto::new);
        List<CafeResponseDto> responseDtoList = cafePage.getContent();

        if (responseDtoList.isEmpty()) {
            throw new IllegalArgumentException("작성된 카페 페이지가 없습니다.");
        }

        return responseDtoList;
    }

}
