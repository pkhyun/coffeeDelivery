package com.sparta.coffeedeliveryproject.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.coffeedeliveryproject.dto.MenuDto;
import com.sparta.coffeedeliveryproject.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CafeCustomRepositoryImpl implements CafeCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<MenuDto> findFavoriteCafesByUserIdWithSearch(Long userId,
                                                             MenuType menuType,
                                                             Long minPrice,
                                                             Long maxPrice,
                                                             Pageable pageable) {
        QMenu menu = QMenu.menu;
        QCafe cafe = QCafe.cafe;
        QCafeLike cafeLike = QCafeLike.cafeLike;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cafeLike.user.userId.eq(userId));

        if (menuType != null) {
            builder.and(menu.menuType.eq(menuType));
        }

        if (minPrice != null) {
            builder.and(menu.menuPrice.goe(minPrice));
        }

        if (maxPrice != null) {
            builder.and(menu.menuPrice.loe(maxPrice));
        }

        List<MenuDto> menuList = jpaQueryFactory
                .select(Projections.constructor(MenuDto.class,
                        menu.menuId,
                        menu.menuName,
                        menu.menuPrice,
                        cafe.cafeName,
                        menu.menuType
                ))
                .from(menu)
                .join(cafe).on(menu.cafe.cafeId.eq(cafe.cafeId))
                .join(cafeLike).on(cafe.cafeId.eq(cafeLike.cafe.cafeId))
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(menuList, pageable, menuList.size());

    }

}
