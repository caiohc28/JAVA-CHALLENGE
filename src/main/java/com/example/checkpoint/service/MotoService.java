package com.example.checkpoint.service;

import com.example.checkpoint.dto.UpdateMotoDTO;
import com.example.checkpoint.exception.ResourceNotFoundException;
import com.example.checkpoint.model.Moto;
import com.example.checkpoint.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> findAll() {
        return motoRepository.findAll();
    }

    public Optional<Moto> findById(Integer id) {
        return motoRepository.findById(id);
    }

    public Optional<Moto> findByPlaca(String placa) {
        return motoRepository.findByPlaca(placa);
    }

    // Método antigo, pode ser mantido para compatibilidade interna ou removido
    public Moto save(Moto moto) {
        return motoRepository.save(moto);
    }

    // Novo método para salvar usando DTO (para o POST)
    public Moto saveFromDTO(UpdateMotoDTO dto) { // Reutilizando UpdateMotoDTO para simplicidade, idealmente seria CreateMotoDTO
        Moto moto = new Moto();
        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());
        moto.setSituacao(dto.getSituacao());
        // Adicionar validação para placa duplicada aqui, se necessário, antes de salvar
        // Ex: if(motoRepository.findByPlaca(dto.getPlaca()).isPresent()) { throw new DataIntegrityViolationException("Placa já cadastrada"); }
        return motoRepository.save(moto);
    }

    // Método antigo, pode ser mantido ou removido
    public Moto update(Integer id, Moto motoDetails) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com id: " + id));
        moto.setPlaca(motoDetails.getPlaca());
        moto.setModelo(motoDetails.getModelo());
        moto.setSituacao(motoDetails.getSituacao());
        return motoRepository.save(moto);
    }

    // Novo método para atualizar usando DTO (para o PUT)
    public Moto updateFromDTO(Integer id, UpdateMotoDTO dto) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com id: " + id));

        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());
        moto.setSituacao(dto.getSituacao());
        // Adicionar validação para placa duplicada aqui, se necessário, antes de salvar (se a placa puder ser alterada)
        return motoRepository.save(moto);
    }

    public void deleteById(Integer id) {
        if (!motoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Moto não encontrada com id: " + id);
        }
        motoRepository.deleteById(id);
    }
}

