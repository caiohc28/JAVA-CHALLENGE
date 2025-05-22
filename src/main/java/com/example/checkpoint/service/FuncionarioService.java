package com.example.checkpoint.service;

import com.example.checkpoint.dto.CreateFuncionarioDTO;
import com.example.checkpoint.dto.FuncionarioResponseDTO;
import com.example.checkpoint.dto.UpdateFuncionarioDTO;
import com.example.checkpoint.exception.ResourceNotFoundException;
import com.example.checkpoint.model.Funcionario;
import com.example.checkpoint.model.Moto;
import com.example.checkpoint.repository.FuncionarioRepository;
import com.example.checkpoint.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private MotoRepository motoRepository;

    private FuncionarioResponseDTO convertToResponseDTO(Funcionario funcionario) {
        Integer motoId = (funcionario.getMoto() != null) ? funcionario.getMoto().getId() : null;
        return new FuncionarioResponseDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getTelefone(),
                funcionario.getTipoFuncionario(),
                motoId
        );
    }

    public Page<FuncionarioResponseDTO> findAll(Pageable pageable) {
        return funcionarioRepository.findAll(pageable).map(this::convertToResponseDTO);
    }

    @Cacheable(value = "funcionarios", key = "#id")
    public Optional<FuncionarioResponseDTO> findById(Integer id) {
        return funcionarioRepository.findById(id).map(this::convertToResponseDTO);
    }

    public Optional<FuncionarioResponseDTO> findByCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf).map(this::convertToResponseDTO);
    }

    @CacheEvict(value = "funcionarios", allEntries = true)
    public FuncionarioResponseDTO saveFromDTO(CreateFuncionarioDTO dto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setTipoFuncionario(dto.getTipoFuncionario());

        if (dto.getMotoId() != null) {
            Moto moto = motoRepository.findById(dto.getMotoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com id: " + dto.getMotoId()));
            funcionario.setMoto(moto);
        }

        Funcionario saved = funcionarioRepository.save(funcionario);
        return convertToResponseDTO(saved);
    }

    @CachePut(value = "funcionarios", key = "#id")
    public FuncionarioResponseDTO updateFromUpdateDTO(Integer id, UpdateFuncionarioDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com id: " + id));

        if (dto.getNome() != null) funcionario.setNome(dto.getNome());
        if (dto.getCpf() != null) funcionario.setCpf(dto.getCpf());
        if (dto.getTelefone() != null) funcionario.setTelefone(dto.getTelefone());
        if (dto.getTipoFuncionario() != null) funcionario.setTipoFuncionario(dto.getTipoFuncionario());

        if (dto.getMotoId() != null) {
            if (dto.getMotoId() == 0) {
                funcionario.setMoto(null);
            } else {
                Moto moto = motoRepository.findById(dto.getMotoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com id: " + dto.getMotoId()));
                funcionario.setMoto(moto);
            }
        }

        Funcionario updated = funcionarioRepository.save(funcionario);
        return convertToResponseDTO(updated);
    }

    @CacheEvict(value = "funcionarios", key = "#id")
    public void deleteById(Integer id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Funcionário não encontrado com id: " + id);
        }
        funcionarioRepository.deleteById(id);
    }
}
