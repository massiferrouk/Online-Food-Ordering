package com.massi.controller;

import com.massi.model.Order;
import com.massi.service.OrderService;
import com.massi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getAllOrdersOfRestaurant(
            @PathVariable Long id,
            @RequestParam(required = false) String orderStatus
    ) throws Exception {

        List<Order> restaurantOrders = orderService.getRestaurantOrders(id, orderStatus);
        return new ResponseEntity<>(restaurantOrders, HttpStatus.OK);

    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @PathVariable String orderStatus
    ) throws Exception {

        Order orderUpdated = orderService.updateOrder(orderId, orderStatus);
        return new ResponseEntity<>(orderUpdated, HttpStatus.OK);

    }

}
