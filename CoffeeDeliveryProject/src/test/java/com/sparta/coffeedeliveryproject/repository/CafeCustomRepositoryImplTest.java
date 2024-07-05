package com.sparta.coffeedeliveryproject.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.coffeedeliveryproject.config.QueryDSLConfig;
import com.sparta.coffeedeliveryproject.dto.MenuDto;
import com.sparta.coffeedeliveryproject.entity.MenuType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({QueryDSLConfig.class})
public class CafeCustomRepositoryImplTest {

    @Autowired
    private CafeCustomRepositoryImpl cafeCustomRepository;

    @Test
    public void testFindFavoriteCafesByUserIdWithSearch() {
        // given
        Long userId = 1L;
        MenuType menuType = MenuType.COFFEE;
        Long minPrice = 1000L;
        Long maxPrice = 5000L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MenuDto> resultPage = cafeCustomRepository.findFavoriteCafesByUserIdWithSearch(userId, menuType, minPrice, maxPrice, pageable);

        // then
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).isNotEmpty();

        MenuDto menuDto = resultPage.getContent().get(0);
        assertThat(menuDto.getMenuId()).isNotNull();
        assertThat(menuDto.getMenuName()).isNotNull();
        assertThat(menuDto.getMenuPrice()).isBetween(minPrice, maxPrice);
        assertThat(menuDto.getCafeName()).isNotNull();
        assertThat(menuDto.getMenuType()).isEqualTo(menuType);

    }
}
