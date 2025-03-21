package com.massi.repository;

import com.massi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByCustomerId(Long UserId);

    public List<Order> findByRestaurantId(Long RestaurantId);

}
