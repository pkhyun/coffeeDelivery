package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Optional<Cafe> findByCafeName(String cafeName);

    @Query("SELECT c.menuList "
        + "FROM Cafe c "
        + "JOIN CafeLike cl ON c.cafeId = cl.cafe.cafeId "
        + "WHERE cl.user.userId = :userId")
    Page<Menu> findFavoriteCafesByUserId(Long userId, Pageable pageable);
}
