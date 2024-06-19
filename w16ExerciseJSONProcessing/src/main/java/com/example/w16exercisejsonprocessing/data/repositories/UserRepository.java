package com.example.w16exercisejsonprocessing.data.repositories;

import com.example.w16exercisejsonprocessing.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Set<User> findAllBySoldBuyerIsNotNull();

}
