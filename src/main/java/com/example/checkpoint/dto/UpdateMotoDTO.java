package com.example.checkpoint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// DTO para atualização de Moto
public class UpdateMotoDTO {

    @NotNull(message = "O ID é obrigatório.")
    private Long id;

    @NotBlank(message = "A placa é obrigatória.")
    @Size(max = 10, message = "A placa deve ter no máximo 10 caracteres.")
    @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$|^[A-Z]{3}[0-9]{4}$", message = "Formato de placa inválido.")
    private String placa;

    @NotBlank(message = "O modelo é obrigatório.")
    @Size(max = 30, message = "O modelo deve ter no máximo 30 caracteres.")
    private String modelo;

    @NotBlank(message = "A situação é obrigatória.")
    @Size(max = 20, message = "A situação deve ter no máximo 20 caracteres.")
    private String situacao;

    // Construtores
    public UpdateMotoDTO() {
    }

    public UpdateMotoDTO(Long id, String placa, String modelo, String situacao) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.situacao = situacao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
