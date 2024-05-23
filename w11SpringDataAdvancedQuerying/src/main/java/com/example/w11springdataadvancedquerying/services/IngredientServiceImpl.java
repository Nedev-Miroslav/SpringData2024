package com.example.w11springdataadvancedquerying.services;

import com.example.w11springdataadvancedquerying.entities.Ingredient;
import com.example.w11springdataadvancedquerying.repositories.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findByNameStartsWith(String name) {
        return this.ingredientRepository.findByNameStartsWith(name);
    }

    @Override
    public List<Ingredient> selectByNames(List<String> ingredient) {
        return this.ingredientRepository.findByNameInOrderByPrice(ingredient);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        this.ingredientRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public void updateAllPrice() {
        this.ingredientRepository.updateAllPrice();
    }

    @Override
    @Transactional
    public void updatePriceByName(List<String> ingredient) {
        this.ingredientRepository.updatePriceByName(ingredient);
    }


}
