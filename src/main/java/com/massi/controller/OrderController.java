package com.massi.controller;

import com.massi.model.Order;
import com.massi.model.User;
import com.massi.request.OrderRequest;
import com.massi.service.OrderService;
import com.massi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(
            @RequestHeader("Authorization") String jwt,
            @RequestBody OrderRequest req
    ) throws Exception {

        Order order = orderService.createOrder(req, jwt);

        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getAllOrdersOfUser(
            @RequestHeader("Authorizartion") String jwt
    ) throws Exception {

        List<Order> userOrders = orderService.getUserOrders(jwt);
        return new ResponseEntity<>(userOrders, HttpStatus.OK);

    }

}
