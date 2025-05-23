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
import org.springframework.cache.annotation.Caching;
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

    // Cacheable para a lista geral (já presente no Controller, mas pode ser redundante ou movido para cá)
    // @Cacheable("funcionarios") // Removido do controller, mantido aqui ou vice-versa
    public Page<FuncionarioResponseDTO> findAll(Pageable pageable) {
        return funcionarioRepository.findAll(pageable).map(this::convertToResponseDTO);
    }

    // Cacheable para busca por ID (já presente no Controller)
    // @Cacheable(value = "funcionarioPorId", key = "#id") // Removido do controller, mantido aqui ou vice-versa
    public Optional<FuncionarioResponseDTO> findById(Integer id) {
        return funcionarioRepository.findById(id).map(this::convertToResponseDTO);
    }

    // Cacheable para busca por CPF (já presente no Controller)
    // @Cacheable(value = "funcionarioPorCpf", key = "#cpf") // Removido do controller, mantido aqui ou vice-versa
    public Optional<FuncionarioResponseDTO> findByCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf).map(this::convertToResponseDTO);
    }

    // Ao salvar, evict todos os caches relevantes (lista geral)
    @Caching(evict = {
            @CacheEvict(value = "funcionarios", allEntries = true),
            @CacheEvict(value = "funcionarioPorCpf", allEntries = true) // Limpa cache por CPF também
    })
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
        // Não usar @CachePut aqui, pois o ID é gerado após salvar.
        // A invalidação acima garante que a próxima busca trará o novo dado.
        return convertToResponseDTO(saved);
    }

    // Ao atualizar, usar @CachePut para o ID específico e @CacheEvict para os demais caches
    @Caching(put = {
            @CachePut(value = "funcionarioPorId", key = "#id")
    }, evict = {
            @CacheEvict(value = "funcionarios", allEntries = true),
            @CacheEvict(value = "funcionarioPorCpf", allEntries = true) // Limpa cache por CPF também
    })
    public FuncionarioResponseDTO updateFromUpdateDTO(Integer id, UpdateFuncionarioDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com id: " + id));

        // Guarda o CPF antigo para invalidar o cache específico se ele mudar
        String oldCpf = funcionario.getCpf();

        if (dto.getNome() != null) funcionario.setNome(dto.getNome());
        if (dto.getCpf() != null) funcionario.setCpf(dto.getCpf());
        if (dto.getTelefone() != null) funcionario.setTelefone(dto.getTelefone());
        if (dto.getTipoFuncionario() != null) funcionario.setTipoFuncionario(dto.getTipoFuncionario());

        if (dto.getMotoId() != null) {
            if (dto.getMotoId() == 0) { // Convenção para desassociar moto
                funcionario.setMoto(null);
            } else {
                Moto moto = motoRepository.findById(dto.getMotoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com id: " + dto.getMotoId()));
                funcionario.setMoto(moto);
            }
        } // Se dto.getMotoId() for null, não faz nada com a moto

        Funcionario updated = funcionarioRepository.save(funcionario);

        // Invalida o cache do CPF antigo se ele foi alterado
        // A anotação @CacheEvict(value = "funcionarioPorCpf", allEntries = true) já cobre isso globalmente.
        // Se quisesse invalidar apenas o CPF antigo: cacheManager.getCache("funcionarioPorCpf").evict(oldCpf);

        return convertToResponseDTO(updated);
    }

    // Ao deletar, evict todos os caches relevantes
    @Caching(evict = {
            @CacheEvict(value = "funcionarios", allEntries = true),
            @CacheEvict(value = "funcionarioPorId", key = "#id"),
            @CacheEvict(value = "funcionarioPorCpf", allEntries = true) // Limpa cache por CPF também
            // Poderia tentar limpar o CPF específico se soubesse qual era antes de deletar,
            // mas limpar tudo é mais seguro e simples.
    })
    public void deleteById(Integer id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Funcionário não encontrado com id: " + id);
        }
        // Se precisar limpar o cache do CPF específico, buscar o funcionário antes de deletar:
        // Optional<Funcionario> func = funcionarioRepository.findById(id);
        funcionarioRepository.deleteById(id);
        // func.ifPresent(f -> cacheManager.getCache("funcionarioPorCpf").evict(f.getCpf()));
    }
}

