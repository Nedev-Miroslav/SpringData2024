package com.example.w11springdataadvancedquerying.services;

import com.example.w11springdataadvancedquerying.entities.Shampoo;
import com.example.w11springdataadvancedquerying.entities.Size;
import com.example.w11springdataadvancedquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findByBrand(String brand) {
        return this.shampooRepository.findByBrand(brand);
    }

    @Override
    public List<Shampoo> findByBrandAndSize(String brand, String size) {
        Size parsed = Size.valueOf(size);

        return this.shampooRepository.findByBrandAndSize(brand, parsed);
    }

    @Override
    public List<Shampoo> findBySizeOrderById(String size) {
        Size parsed = Size.valueOf(size);

        return this.shampooRepository.findBySizeOrderById(parsed);
    }

    @Override
    public List<Shampoo> findByIngredient(String ingredient) {
        return this.shampooRepository.findByIngredient(ingredient);
    }

    @Override
    public List<Shampoo> findByIngredient(List<String> ingredients) {
        return this.shampooRepository.findByIngredients(ingredients);
    }

    @Override
    public List<Shampoo> findBySizeOrLabelId(String size, long labelId) {
        Size parsed = Size.valueOf(size);
        return this.shampooRepository.findBySizeOrLabelIdOrderByPrice(parsed, labelId);
    }

    @Override
    public List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(String price){
        BigDecimal parsed = new BigDecimal(price);

        return this.shampooRepository.findByPriceGreaterThanOrderByPriceDesc(parsed);


    }

    @Override
    public Long countWithPriceLowerThan(String price) {
        BigDecimal parsed = new BigDecimal(price);

        return this.shampooRepository.countByPriceLessThan(parsed);
    }

    @Override
    public List<Shampoo> findWithIngredientCountLessThan(int count) {
        return this.shampooRepository.findByIngredientCountLessThan(count);
    }

}
