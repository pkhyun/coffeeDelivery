package com.sparta.coffeedeliveryproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "menu")
public class Menu extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "menu_price", nullable = false)
    private Long menuPrice;

    @ManyToOne
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MenuType menuType;

    public Menu(String menuName, Long menuPrice, Cafe cafe, MenuType menuType) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.cafe = cafe;
        this.menuType = menuType;
    }

}
