package com.squadlobo.api.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Cliente  implements Serializable {	

	private static final long serialVersionUID = 1L;

	@Id
	@Size(min = 11, max=11, message = "O CPF deve conter 11 digitos!")
	@CPF(message = "CPF inválido!")
	private String cpf;
	
	@NotBlank(message = "O nome não pode ser nulo ou vazio!")
	private String nome;
	
	@Column( nullable = false )
	private ZonedDateTime dataNascimento;
	
	@Size(min = 10, max=11, message = "O telefone deve conter 10 ou 11 digitos!")
	@NotBlank(message = "O telefone não pode ser nulo ou vazio!")
	private String telefone;

	@Column( nullable = false ) 
	private Double rendaMensal;		

	public Cliente() {		
	}
	
	public Cliente(String nome, String cpf, ZonedDateTime dataNascimento, String telefone, Double rendaMensal) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.rendaMensal = rendaMensal;
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

	public ZonedDateTime getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(ZonedDateTime dataNascimento) {
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
