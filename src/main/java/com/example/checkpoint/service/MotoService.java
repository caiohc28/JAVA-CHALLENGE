package com.example.checkpoint.service;

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

import java.util.Optional;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public Page<Moto> findAll(Pageable pageable) {
        return motoRepository.findAll(pageable);
    }

    @Cacheable(value = "motos", key = "#id")
    public Optional<Moto> findById(Integer id) {
        return motoRepository.findById(id);
    }

    public Optional<Moto> findByPlaca(String placa) {
        return motoRepository.findByPlaca(placa);
    }

    @CacheEvict(value = "motos", allEntries = true)
    public Moto saveFromDTO(UpdateMotoDTO dto) {
        Moto moto = new Moto();
        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());
        moto.setSituacao(dto.getSituacao());
        return motoRepository.save(moto);
    }

    @CachePut(value = "motos", key = "#id")
    public Moto updateFromDTO(Integer id, UpdateMotoDTO dto) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com id: " + id));

        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());
        moto.setSituacao(dto.getSituacao());
        return motoRepository.save(moto);
    }

    @CacheEvict(value = "motos", key = "#id")
    public void deleteById(Integer id) {
        if (!motoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Moto não encontrada com id: " + id);
        }
        motoRepository.deleteById(id);
    }
}

