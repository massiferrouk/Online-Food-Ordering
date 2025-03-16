package com.massi.service;

import com.massi.model.IngredientCategory;
import com.massi.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientsCategoriesByRestaurantId(Long Id) throws Exception;

    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception;

    public IngredientsItem updateStock(Long id) throws Exception;
}
