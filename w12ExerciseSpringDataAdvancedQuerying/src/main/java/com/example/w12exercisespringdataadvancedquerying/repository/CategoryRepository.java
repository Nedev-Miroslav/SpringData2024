package com.example.w12exercisespringdataadvancedquerying.repository;


import com.example.w12exercisespringdataadvancedquerying.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
