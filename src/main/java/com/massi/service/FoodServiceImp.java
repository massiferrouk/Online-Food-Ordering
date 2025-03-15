package com.massi.service;

import com.massi.model.Food;
import com.massi.model.Restaurant;
import com.massi.request.CreateFoodRequest;

import java.util.List;

public class FoodServiceImp implements FoodService {
    @Override
    public Food createFood(CreateFoodRequest req, Restaurant restaurant) {
        return null;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {

    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isSeasonal, String foodCategory) {
        return List.of();
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return List.of();
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        return null;
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        return null;
    }
}
