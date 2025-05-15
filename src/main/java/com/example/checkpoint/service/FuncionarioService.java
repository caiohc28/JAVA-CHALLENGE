package com.example.checkpoint.service;


import com.example.checkpoint.dto.CreateFuncionarioDTO;
import com.example.checkpoint.dto.FuncionarioResponseDTO;
import com.example.checkpoint.dto.UpdateFuncionarioDTO;
import com.example.checkpoint.model.Funcionario;
import com.example.checkpoint.model.Moto;
import com.example.checkpoint.repository.FuncionarioRepository;
import com.example.checkpoint.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Removida a definição duplicada de ResourceNotFoundException, assumindo que está em exception.GlobalExceptionHandler

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private MotoRepository motoRepository;

    // Método para converter Entidade Funcionario para FuncionarioResponseDTO
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

    public List<FuncionarioResponseDTO> findAll() {
        return funcionarioRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<FuncionarioResponseDTO> findById(Integer id) {
        return funcionarioRepository.findById(id).map(this::convertToResponseDTO);
    }

    public Optional<FuncionarioResponseDTO> findByCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf).map(this::convertToResponseDTO);
    }

    public FuncionarioResponseDTO saveFromDTO(CreateFuncionarioDTO dto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setTipoFuncionario(dto.getTipoFuncionario());

        if (dto.getMotoId() != null) {
            Moto moto = motoRepository.findById(dto.getMotoId())
                    .orElseThrow(() -> new com.example.checkpoint.exception.ResourceNotFoundException("Moto não encontrada com id: " + dto.getMotoId()));
            funcionario.setMoto(moto);
        }
        Funcionario savedFuncionario = funcionarioRepository.save(funcionario);
        return convertToResponseDTO(savedFuncionario);
    }

    public FuncionarioResponseDTO updateFromUpdateDTO(Integer id, UpdateFuncionarioDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new com.example.checkpoint.exception.ResourceNotFoundException("Funcionário não encontrado com id: " + id));

        if (dto.getNome() != null) funcionario.setNome(dto.getNome());
        if (dto.getCpf() != null) funcionario.setCpf(dto.getCpf()); // Adicionar validação de CPF único se alterado
        if (dto.getTelefone() != null) funcionario.setTelefone(dto.getTelefone());
        if (dto.getTipoFuncionario() != null) funcionario.setTipoFuncionario(dto.getTipoFuncionario());

        if (dto.getMotoId() != null) {
            if (dto.getMotoId() == 0) { // Convenção para desassociar moto
                funcionario.setMoto(null);
            } else {
                Moto moto = motoRepository.findById(dto.getMotoId())
                        .orElseThrow(() -> new com.example.checkpoint.exception.ResourceNotFoundException("Moto não encontrada com id: " + dto.getMotoId()));
                funcionario.setMoto(moto);
            }
        } // Se motoId for null no DTO, a associação existente não é alterada.

        Funcionario updatedFuncionario = funcionarioRepository.save(funcionario);
        return convertToResponseDTO(updatedFuncionario);
    }

    public void deleteById(Integer id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new com.example.checkpoint.exception.ResourceNotFoundException("Funcionário não encontrado com id: " + id);
        }
        funcionarioRepository.deleteById(id);
    }
}

