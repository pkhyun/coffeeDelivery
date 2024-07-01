package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.dto.MenuDto;
import com.sparta.coffeedeliveryproject.entity.MenuType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CafeCustomRepository {

    Page<MenuDto> findFavoriteCafesByUserIdWithSearch(Long userId, MenuType menuType, Long minPrice, Long maxPrice, Pageable pageable);

}
