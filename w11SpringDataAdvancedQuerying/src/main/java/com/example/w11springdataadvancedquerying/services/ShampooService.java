package com.example.w11springdataadvancedquerying.services;

import com.example.w11springdataadvancedquerying.entities.Shampoo;

import java.util.List;

public interface ShampooService {
    List<Shampoo> findByBrand(String brand);


    List<Shampoo> findByBrandAndSize(String brand, String size);

    List<Shampoo> findBySizeOrderById(String size);


    List<Shampoo> findByIngredient(String ingredient);

    List<Shampoo> findByIngredient(List<String> ingredient);

    List<Shampoo> findBySizeOrLabelId(String size, long labelId);

    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(String price);

    Long countWithPriceLowerThan(String price);

    List<Shampoo> findWithIngredientCountLessThan(int count);
}
