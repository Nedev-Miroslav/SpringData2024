package com.example.w11springdataadvancedquerying.services;

import com.example.w11springdataadvancedquerying.entities.Ingredient;

import java.util.List;


public interface IngredientService {
    List<Ingredient> findByNameStartsWith(String name);

    List<Ingredient> selectByNames(List<String> ingredient);


    void deleteByName(String name);

    void updateAllPrice();

    void updatePriceByName(List<String> ingredient);
}

