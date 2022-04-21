package com.squadlobo.api.dto;

import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.ZonedDateTime;


public class ClienteDTO {

    @Id
    @Size(min = 11, max = 11, message = "O CPF deve conter 11 digitos!")
    @CPF(message = "CPF inválido!")
    private String cpf;

    @NotBlank(message = "O nome não pode ser nulo ou vazio!")
    private String nome;

    private LocalDate dataNascimento;

    @Size(min = 10, max = 11, message = "O telefone deve conter 10 ou 11 digitos!")
    @NotBlank(message = "O telefone não pode ser nulo ou vazio!")
    private String telefone;
    private Double rendaMensal;


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Double getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(Double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }
}
