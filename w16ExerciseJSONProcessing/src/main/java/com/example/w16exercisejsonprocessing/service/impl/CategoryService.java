package com.example.w16exercisejsonprocessing.service.impl;

import com.example.w16exercisejsonprocessing.service.dtos.imports.CategoryByProductsDto;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    void seedCategories() throws IOException;

    List<CategoryByProductsDto> getAllCategoriesByProducts();

    void printAllCategoriesByProducts();
}
