package com.example.checkpoint.controller;

import com.example.checkpoint.dto.CreateMotoDTO; // Importar o novo DTO
import com.example.checkpoint.dto.UpdateMotoDTO;
import com.example.checkpoint.model.Moto;
import com.example.checkpoint.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
    @Cacheable("motos")
    public ResponseEntity<Page<Moto>> getAllMotos(Pageable pageable) {
        Page<Moto> motos = motoService.findAll(pageable);
        return ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "motoPorId", key = "#id")
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
    @CacheEvict(value = {"motos", "motoPorId", "motoPorPlaca"}, allEntries = true) // Limpar caches relevantes
    public ResponseEntity<Moto> createMoto(@Valid @RequestBody CreateMotoDTO motoDTO) { // Usar CreateMotoDTO
        try {
            // Chamar um método no service que aceite CreateMotoDTO (será criado a seguir)
            Moto novaMoto = motoService.saveFromCreateDTO(motoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaMoto);
        } catch (Exception e) {
            // Considerar logar o erro e retornar uma resposta mais específica se possível
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @CachePut(value = "motoPorId", key = "#id") // Atualizar cache individual
    @CacheEvict(value = {"motos", "motoPorPlaca"}, allEntries = true) // Limpar outros caches
    public ResponseEntity<Moto> updateMoto(@PathVariable Integer id, @Valid @RequestBody UpdateMotoDTO motoDTO) {
        try {
            // Remover a validação @NotNull do ID no UpdateMotoDTO
            Moto motoAtualizada = motoService.updateFromDTO(id, motoDTO);
            return ResponseEntity.ok(motoAtualizada);
        } catch (com.example.checkpoint.exception.ResourceNotFoundException e) { // Usar exceção específica
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Considerar logar o erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = {"motos", "motoPorId", "motoPorPlaca"}, allEntries = true) // Limpar todos os caches relevantes
    public ResponseEntity<Void> deleteMoto(@PathVariable Integer id) {
        try {
            motoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (com.example.checkpoint.exception.ResourceNotFoundException e) { // Usar exceção específica
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Considerar logar o erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

