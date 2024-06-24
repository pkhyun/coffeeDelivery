package com.sparta.coffeedeliveryproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Order extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private int orderPrice;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ElementCollection
    @CollectionTable(name = "menu_names", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "menu_name")
    private List<String> menuNames;

    public Order(int orderPrice, OrderStatus orderStatus, User user, Cafe cafe, List<String> menuNames) {
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.user = user;
        this.cafe = cafe;
        this.menuNames = menuNames;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

}
