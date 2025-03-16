package com.massi.service;

import com.massi.model.IngredientCategory;
import com.massi.model.IngredientsItem;
import com.massi.model.Restaurant;
import com.massi.repository.IngredientCategoryRepository;
import com.massi.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImp implements IngredientsService {

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);

        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {

        Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);

        if (opt.isEmpty()) {
            throw new Exception("Ingredient Category not found");
        }

        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientsCategoriesByRestaurantId(Long Id) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(Id);

        return ingredientCategoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);

        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem ingredient = ingredientItemRepository.save(item);
        category.getIngredients().add(ingredient);

        return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        return ingredientItemRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {

        Optional<IngredientsItem> opt = ingredientItemRepository.findById(id);

        if (opt.isEmpty()) {
            throw new Exception("Ingredient Item not found");
        }

        IngredientsItem item = opt.get();
        item.setInStock(!item.isInStock());
        return ingredientItemRepository.save(item);
    }
}
