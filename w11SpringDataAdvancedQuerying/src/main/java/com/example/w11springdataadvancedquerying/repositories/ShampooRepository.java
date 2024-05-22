package com.example.w11springdataadvancedquerying.repositories;

import com.example.w11springdataadvancedquerying.entities.Shampoo;

import com.example.w11springdataadvancedquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findByBrand(String brand);

    List<Shampoo> findByBrandAndSize(String brand, Size size);

    List<Shampoo> findBySizeOrderById(Size parsed);

    @Query(value = "SELECT s FROM Shampoo AS s " +
            "JOIN s.ingredients AS i " +
            "WHERE i.name = :ingredient")
    List<Shampoo> findByIngredient(@Param(value = "ingredient") String ingredient);


    @Query(value = "SELECT s FROM Shampoo AS s " +
            "JOIN s.ingredients AS i " +
            "WHERE i.name IN :ingredients")
    List<Shampoo> findByIngredients(List<String> ingredients);


    List<Shampoo> findBySizeOrLabelIdOrderByPrice(Size parsed, long labelId);


    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    long countByPriceLessThan(BigDecimal price);



    @Query("SELECT s FROM Shampoo AS s " +
            "WHERE SIZE(s.ingredients) < :count")
    List<Shampoo> findByIngredientCountLessThan(int count);

// други решения
//    @Query("SELECT s FROM Shampoo AS s JOIN s.ingredients i GROUP BY s HAVING COUNT(i) < :count")
//    List<Shampoo> findByIngredientCountLessThan(int count);

//    @Query("SELECT s FROM Shampoo s WHERE (SELECT COUNT(i) FROM s.ingredients i) < :count")
//    List<Shampoo> findByIngredientCountLessThan(int count);







}