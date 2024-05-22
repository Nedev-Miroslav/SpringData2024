package com.example.w11springdataadvancedquerying.repositories;

import com.example.w11springdataadvancedquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByNameStartsWith(String name);

    List<Ingredient> findByNameInOrderByPrice(List<String> ingredient);

    void deleteByName(String name);

    @Query("UPDATE Ingredient AS i " +
            "SET i.price = i.price * 1.10")
    @Modifying
    void updateAllPrice();

    @Query("UPDATE Ingredient AS i " +
            "SET i.price = i.price * 0.90 " +
            "WHERE i.name IN :ingredient")
    @Modifying
    void updatePriceByName(List<String> ingredient);
}
