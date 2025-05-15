package com.example.checkpoint.controller;

import com.example.checkpoint.dto.CreateFuncionarioDTO;
import com.example.checkpoint.dto.FuncionarioResponseDTO;
import com.example.checkpoint.dto.UpdateFuncionarioDTO;
import com.example.checkpoint.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> getAllFuncionarios() {
        List<FuncionarioResponseDTO> funcionarios = funcionarioService.findAll();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> getFuncionarioById(@PathVariable Integer id) {
        Optional<FuncionarioResponseDTO> funcionarioDTO = funcionarioService.findById(id);
        return funcionarioDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<FuncionarioResponseDTO> getFuncionarioByCpf(@PathVariable String cpf) {
        Optional<FuncionarioResponseDTO> funcionarioDTO = funcionarioService.findByCpf(cpf);
        return funcionarioDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> createFuncionario(@Valid @RequestBody CreateFuncionarioDTO funcionarioDTO) {
        try {
            FuncionarioResponseDTO novoFuncionario = funcionarioService.saveFromDTO(funcionarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoFuncionario);
        } catch (com.example.checkpoint.exception.ResourceNotFoundException e) {
            return ResponseEntity.badRequest().build(); // Ou um erro mais específico, como 404 se a moto não for encontrada
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> updateFuncionario(@PathVariable Integer id, @Valid @RequestBody UpdateFuncionarioDTO funcionarioDTO) {
        try {
            FuncionarioResponseDTO funcionarioAtualizado = funcionarioService.updateFromUpdateDTO(id, funcionarioDTO);
            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (com.example.checkpoint.exception.ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Outros erros
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Integer id) {
        try {
            funcionarioService.deleteById(id);
            return ResponseEntity.noContent().build(); // Correto, sem corpo de resposta
        } catch (com.example.checkpoint.exception.ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

