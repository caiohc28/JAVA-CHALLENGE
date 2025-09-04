package com.example.checkpoint.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tb_moto")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moto")
    private Integer id;

    @NotBlank(message = "A placa é obrigatória.")
    @Size(max = 10, message = "A placa deve ter no máximo 10 caracteres.")
    @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$|^[A-Z]{3}[0-9]{4}$", message = "Formato de placa inválido.")
    @Column(name = "placa", unique = true, nullable = false, length = 10)
    private String placa;

    @NotBlank(message = "O modelo é obrigatório.")
    @Size(max = 30, message = "O modelo deve ter no máximo 30 caracteres.")
    @Column(name = "modelo", nullable = false, length = 30)
    private String modelo;

    @NotBlank(message = "A situação é obrigatória.")
    @Size(max = 20, message = "A situação deve ter no máximo 20 caracteres.")
    @Column(name = "situacao", nullable = false, length = 20)
    private String situacao;

    @OneToMany(mappedBy = "moto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference // Adicionado para gerenciar a serialização
    private List<Funcionario> funcionarios;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }
}

