package com.massi.request;

import lombok.Data;

@Data
public class IngredientItemRequest {

    private Long restaurantId;
    private String ingredientName;
    private Long categoryId;
}
