package com.example.checkpoint.repository;

import com.example.checkpoint.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Integer> {
    Optional<Moto> findByPlaca(String placa);
}

