package com.massi.controller;

import com.massi.model.IngredientCategory;
import com.massi.model.IngredientsItem;
import com.massi.request.IngredientCategoryRequest;
import com.massi.request.IngredientItemRequest;
import com.massi.service.IngredientsService;
import com.massi.service.RestaurantService;
import com.massi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req
    ) throws Exception {

        IngredientCategory ingredientCategory = ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);

    }

    @PostMapping("/item")
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientItemRequest req
    ) throws Exception {

        IngredientsItem item = ingredientsService.createIngredientItem
                (req.getRestaurantId(), req.getIngredientName(), req.getCategoryId());

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("{/id}/stock")
    public ResponseEntity<IngredientsItem> updateStock(
            @PathVariable Long id
    ) throws Exception {

        IngredientsItem updatedIngredientsItem = ingredientsService.updateStock(id);
        return new ResponseEntity<>(updatedIngredientsItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getAllRestaurantIngredients(
            @PathVariable Long id
    ) throws Exception {

        List<IngredientsItem> ingredients =
                ingredientsService.findRestaurantIngredients(id);

        return new ResponseEntity<>(ingredients, HttpStatus.OK);

    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getAllRestaurantIngredientsCategories(
            @PathVariable Long id
    ) throws Exception {

        List<IngredientCategory> ingredientsCategories =
                ingredientsService.findIngredientsCategoriesByRestaurantId(id);

        return new ResponseEntity<>(ingredientsCategories, HttpStatus.OK);

    }

}
