package com.example.w10springdataintro.services;

import com.example.w10springdataintro.entities.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategories();
}
