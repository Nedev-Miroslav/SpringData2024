package com.example.football.repository;

import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {


    Optional<Player> getByEmail(String email);

    @Query(value = "SELECT p FROM Player p WHERE p.birthDate > '1995-01-01' AND p.birthDate < '2003-01-01'")
    List<Player> findAllPlayer();

}
