package com.example.checkpoint.controller;

import com.example.checkpoint.dto.UpdateMotoDTO; // Importar o novo DTO
import com.example.checkpoint.model.Moto;
import com.example.checkpoint.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<List<Moto>> getAllMotos() {
        List<Moto> motos = motoService.findAll();
        return ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> getMotoById(@PathVariable Integer id) {
        Optional<Moto> moto = motoService.findById(id);
        // A resposta aqui já está enxuta devido ao @JsonIgnore na entidade Moto
        return moto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<Moto> getMotoByPlaca(@PathVariable String placa) {
        Optional<Moto> moto = motoService.findByPlaca(placa);
        return moto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Moto> createMoto(@Valid @RequestBody UpdateMotoDTO motoDTO) { // Usar DTO para criação também é uma boa prática
        // Por simplicidade, vamos assumir que o UpdateMotoDTO pode ser usado para criação também,
        // mas idealmente teríamos um CreateMotoDTO.
        try {
            Moto novaMoto = motoService.saveFromDTO(motoDTO); // Supõe um método saveFromDTO no serviço
            return ResponseEntity.status(HttpStatus.CREATED).body(novaMoto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> updateMoto(@PathVariable Integer id, @Valid @RequestBody UpdateMotoDTO motoDTO) { // Usando UpdateMotoDTO
        try {
            Moto motoAtualizada = motoService.updateFromDTO(id, motoDTO); // Novo método no serviço
            return ResponseEntity.ok(motoAtualizada);
        } catch (RuntimeException e) { // Idealmente, capturar ResourceNotFoundException especificamente
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
        } catch (RuntimeException e) { // Idealmente, capturar ResourceNotFoundException
            if (e.getMessage().contains("não encontrada")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

