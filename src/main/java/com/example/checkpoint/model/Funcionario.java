package com.example.checkpoint.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    private Integer id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    // Mantendo o regex do CPF que permite formatos com ou sem pontos/hífen
    @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$", message = "Formato de CPF inválido. Use XXX.XXX.XXX-XX ou XXXXXXXXXXX.")
    @Column(name = "cpf", unique = true, nullable = false, length = 14)
    private String cpf;

    @Size(max = 15, message = "O telefone deve ter no máximo 15 caracteres.")
    // Regex ajustada para ser um pouco mais permissiva com formatos comuns (incluindo 9 opcional no início)
    @Pattern(regexp = "^\\(?[1-9]{2}\\)?[ -]?9?[0-9]{4}[ -]?[0-9]{4}$", message = "Formato de telefone inválido.")
    @Column(name = "telefone", length = 15)
    private String telefone;

    @Size(max = 20, message = "O tipo de funcionário deve ter no máximo 20 caracteres.")
    @Column(name = "tipo_funcionario", length = 20)
    private String tipoFuncionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moto_id_moto", referencedColumnName = "id_moto")
    @JsonBackReference
    private Moto moto;

    // Getters and Setters
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

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }
}

