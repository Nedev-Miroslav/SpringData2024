package com.example.w18exercisexmlprocessing.data.repositories;

import com.example.w18exercisexmlprocessing.data.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
