package com.massi.service;

import com.massi.model.Category;
import com.massi.model.Restaurant;
import com.massi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    public RestaurantService restaurantService;

    @Override
    public Category createCategory(String name, Long userId) throws Exception {

        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> FindCategoryByRestaurantId(Long id) throws Exception {
        return categoryRepository.findByRestaurantId(id);
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> opt = categoryRepository.findById(id);

        if (opt.isEmpty()) {
            throw new Exception("category not found");
        }

        return opt.get();
    }
}
