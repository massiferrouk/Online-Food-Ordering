package com.massi.controller;

import com.massi.model.Category;
import com.massi.model.User;
import com.massi.service.CategoryService;
import com.massi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Category category
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Category createCategory = categoryService.createCategory(category.getName(), user.getId());

        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategories(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Category> categories = categoryService.FindCategoriesByRestaurantId(user.getId());

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategory(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Category category = categoryService.findCategoryById(id);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
