package com.example.checkpoint.service;

import com.example.checkpoint.dto.CreateMotoDTO; // Importar CreateMotoDTO
import com.example.checkpoint.dto.UpdateMotoDTO;
import com.example.checkpoint.exception.ResourceNotFoundException;
import com.example.checkpoint.model.Moto;
import com.example.checkpoint.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar Transactional

import java.util.Optional;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    @Transactional(readOnly = true) // Boa prática para métodos de leitura
    public Page<Moto> findAll(Pageable pageable) {
        return motoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "motoPorId", key = "#id") // Ajustado o nome do cache para corresponder ao controller
    public Optional<Moto> findById(Integer id) {
        return motoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "motoPorPlaca", key = "#placa") // Adicionado cache para busca por placa
    public Optional<Moto> findByPlaca(String placa) {
        return motoRepository.findByPlaca(placa);
    }

    // Método para salvar usando CreateMotoDTO (sem ID)
    @Transactional // Adicionar Transactional para escrita
    @CacheEvict(value = {"motos", "motoPorId", "motoPorPlaca"}, allEntries = true) // Limpar caches relevantes
    public Moto saveFromCreateDTO(CreateMotoDTO dto) {
        Moto moto = new Moto();
        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());
        moto.setSituacao(dto.getSituacao());
        // Não definir ID aqui, será gerado pelo banco
        return motoRepository.save(moto);
    }

    // Método saveFromDTO antigo (usando UpdateMotoDTO) removido ou comentado se não for mais usado diretamente pelo controller
    /*
    @Transactional
    @CacheEvict(value = "motos", allEntries = true)
    public Moto saveFromDTO(UpdateMotoDTO dto) {
        Moto moto = new Moto();
        // ID não deve ser setado aqui se for criação
        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());
        moto.setSituacao(dto.getSituacao());
        return motoRepository.save(moto);
    }
    */

    @Transactional // Adicionar Transactional para escrita
    @CachePut(value = "motoPorId", key = "#id") // Atualizar cache individual
    @CacheEvict(value = {"motos", "motoPorPlaca"}, allEntries = true) // Limpar outros caches
    public Moto updateFromDTO(Integer id, UpdateMotoDTO dto) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com id: " + id));

        // Atualizar apenas os campos presentes no DTO (se necessário, adicionar verificações de nulidade)
        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());
        moto.setSituacao(dto.getSituacao());
        return motoRepository.save(moto); // Persistir as alterações
    }

    @Transactional // Adicionar Transactional para escrita
    @CacheEvict(value = {"motos", "motoPorId", "motoPorPlaca"}, allEntries = true) // Limpar todos os caches relevantes
    public void deleteById(Integer id) {
        // findById já lança ResourceNotFoundException se não encontrar
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com id: " + id));
        motoRepository.delete(moto);
    }
}

