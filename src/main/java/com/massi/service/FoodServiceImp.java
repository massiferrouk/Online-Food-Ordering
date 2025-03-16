package com.massi.service;

import com.massi.model.Category;
import com.massi.model.Food;
import com.massi.model.Restaurant;
import com.massi.repository.CategoryRepository;
import com.massi.repository.FoodRepository;
import com.massi.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImp implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest req,Category category, Restaurant restaurant) {

        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setImages(req.getImages());
        food.setIngredients(req.getIngredientsItems());
        food.setSeasonal(req.isSeasional());
        food.setVegetarian(req.isVegetarian());

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.delete(food);
    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantId,
                                         boolean isVegetarian,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory
    ) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        
        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }
        
        if (isNonveg) {
            foods = filterByNonveg(foods, isNonveg);
        }
        
        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }

        if(foodCategory!=null && !foodCategory.isEmpty()){
            foods = filterByCategory(foods, foodCategory);
        }
        return List.of();
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream()
                .filter(food -> {
                    if(food.getFoodCategory()!=null){
                        return food.getFoodCategory().getName().equals(foodCategory);
                    }
                    return false;
                }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream()
                .filter(food -> food.isSeasonal() == isSeasonal)
                .collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream()
                .filter(food -> !food.isVegetarian())
                .collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream()
                .filter(food -> food.isVegetarian() == isVegetarian)
                .collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (optionalFood.isEmpty()) {
            throw new Exception("food doesn't exist...");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());

        return foodRepository.save(food);
    }
}
