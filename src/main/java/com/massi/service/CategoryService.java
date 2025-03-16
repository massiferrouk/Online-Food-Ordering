package com.massi.service;

import com.massi.model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(String name, Long userId) throws Exception;

    public List<Category> FindCategoryByRestaurantId(Long id) throws Exception;

    public Category findCategoryById(Long id) throws Exception;
}
