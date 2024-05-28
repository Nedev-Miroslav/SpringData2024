package com.example.w12exercisespringdataadvancedquerying.service;


import com.example.w12exercisespringdataadvancedquerying.model.entity.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
