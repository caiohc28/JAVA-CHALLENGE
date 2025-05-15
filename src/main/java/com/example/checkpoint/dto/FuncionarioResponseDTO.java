package com.example.checkpoint.dto;

// DTO para a resposta de Funcionario, para evitar aninhar o objeto Moto completo
public class FuncionarioResponseDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private String tipoFuncionario;
    private Integer motoId; // Apenas o ID da moto, se associada

    // Construtores
    public FuncionarioResponseDTO() {
    }

    public FuncionarioResponseDTO(Integer id, String nome, String cpf, String telefone, String tipoFuncionario, Integer motoId) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.tipoFuncionario = tipoFuncionario;
        this.motoId = motoId;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

