package com.example.w18exercisexmlprocessing.data.repositories;

import com.example.w18exercisexmlprocessing.data.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {



}
