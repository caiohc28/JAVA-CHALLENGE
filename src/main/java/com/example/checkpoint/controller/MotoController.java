package com.example.checkpoint.controller;

import com.example.checkpoint.dto.UpdateMotoDTO;
import com.example.checkpoint.model.Moto;
import com.example.checkpoint.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    @Cacheable("motos") // Cache para a listagem de motos
    public ResponseEntity<Page<Moto>> getAllMotos(Pageable pageable) {
        Page<Moto> motos = motoService.findAll(pageable);
        return ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "motoPorId", key = "#id") // Cache individual por ID
    public ResponseEntity<Moto> getMotoById(@PathVariable Integer id) {
        Optional<Moto> moto = motoService.findById(id);
        return moto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/placa/{placa}")
    @Cacheable(value = "motoPorPlaca", key = "#placa")
    public ResponseEntity<Moto> getMotoByPlaca(@PathVariable String placa) {
        Optional<Moto> moto = motoService.findByPlaca(placa);
        return moto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Moto> createMoto(@Valid @RequestBody UpdateMotoDTO motoDTO) {
        try {
            Moto novaMoto = motoService.saveFromDTO(motoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaMoto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> updateMoto(@PathVariable Integer id, @Valid @RequestBody UpdateMotoDTO motoDTO) {
        try {
            Moto motoAtualizada = motoService.updateFromDTO(id, motoDTO);
            return ResponseEntity.ok(motoAtualizada);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrada")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMoto(@PathVariable Integer id) {
        try {
            motoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrada")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
