package com.massi.controller;

import com.massi.model.Category;
import com.massi.model.Food;
import com.massi.model.Restaurant;
import com.massi.model.User;
import com.massi.request.CreateFoodRequest;
import com.massi.request.CreateRestaurantRequest;
import com.massi.response.MessageResponse;
import com.massi.service.FoodService;
import com.massi.service.RestaurantService;
import com.massi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Food> createFood(
            @RequestBody CreateFoodRequest req,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(
            @RequestHeader("Authorization") String jwt,
            @PathVariable long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);

        MessageResponse res = new MessageResponse();
        res.setMessage("Successfully deleted food");
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateAvailabilityStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }

}
