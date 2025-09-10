package com.example.checkpoint.dto;

public class MotoResponseDTO {
    private Integer id;
    private String placa;
    private String modelo;
    private String situacao;

    // Construtores
    public MotoResponseDTO() {}

    public MotoResponseDTO(Integer id, String placa, String modelo, String situacao) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.situacao = situacao;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }
}