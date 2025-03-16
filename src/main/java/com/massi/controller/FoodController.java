package com.massi.controller;

import com.massi.model.Food;
import com.massi.model.Restaurant;
import com.massi.model.User;
import com.massi.service.FoodService;
import com.massi.service.RestaurantService;
import com.massi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foodList = foodService.searchFood(keyword);

        return new ResponseEntity<>(foodList, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long restaurantId,
            @RequestParam boolean vegetarian,
            @RequestParam boolean nonveg,
            @RequestParam boolean seasonal,
            @RequestParam(required = false) String food_category
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getRestaurantFood(restaurantId,
                vegetarian, nonveg, seasonal, food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
