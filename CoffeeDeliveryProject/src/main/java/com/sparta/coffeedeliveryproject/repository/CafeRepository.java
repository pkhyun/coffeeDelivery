package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.Menu;
import com.sparta.coffeedeliveryproject.entity.MenuType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Optional<Cafe> findByCafeName(String cafeName);

    @Query("SELECT c.menuList "
        + "FROM Cafe c "
        + "JOIN CafeLike cl ON c.cafeId = cl.cafe.cafeId "
        + "WHERE cl.user.userId = :userId")
    Page<Menu> findFavoriteCafesByUserId(Long userId, Pageable pageable);

    @Query("SELECT m FROM Menu m " +
            "JOIN Cafe c ON m.cafe.cafeId = c.cafeId " +
            "JOIN CafeLike cl ON c.cafeId = cl.cafe.cafeId " +
            "WHERE cl.user.userId = :userId " +
            "AND (:menuType IS NULL OR m.menuType = :menuType) " +
            "AND (:minPrice IS NULL OR m.menuPrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR m.menuPrice <= :maxPrice)")
    Page<Menu> findFavoriteCafesByUserIdWithSearch(@Param("userId") Long userId,
                                                   @Param("menuType") MenuType menuType,
                                                   @Param("minPrice") Long minPrice,
                                                   @Param("maxPrice") Long maxPrice,
                                                   Pageable pageable);

}
