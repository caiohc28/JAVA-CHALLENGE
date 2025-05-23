package com.example.checkpoint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Adicione um novo pacote dto se ainda não existir: src/main/java/com/example/checkpoint/dto

public class CreateFuncionarioDTO {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$", message = "Formato de CPF inválido. Use XXX.XXX.XXX-XX ou XXXXXXXXXXX.")
    private String cpf;

    @Size(max = 15, message = "O telefone deve ter no máximo 15 caracteres.")
    @Pattern(regexp = "^\\(?[1-9]{2}\\)?[ -]?(?:[2-8]|9[1-9])[0-9]{3}[ -]?[0-9]{4}$", message = "Formato de telefone inválido.")
    private String telefone;

    @Size(max = 20, message = "O tipo de funcionário deve ter no máximo 20 caracteres.")
    private String tipoFuncionario;

    private Integer motoId; // ID da moto para associar, opcional

    // Construtores
    public CreateFuncionarioDTO() {
    }

    public CreateFuncionarioDTO( String nome, String cpf, String telefone, String tipoFuncionario, Integer motoId) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.tipoFuncionario = tipoFuncionario;
        this.motoId = motoId;
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(String tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    public Integer getMotoId() {
        return motoId;
    }

    public void setMotoId(Integer motoId) {
        this.motoId = motoId;
    }
}

