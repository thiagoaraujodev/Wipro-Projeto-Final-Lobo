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

	//private static final long serialVersionUID = 1L;

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
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((rendaMensal == null) ? 0 : rendaMensal.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (rendaMensal == null) {
			if (other.rendaMensal != null)
				return false;
		} else if (!rendaMensal.equals(other.rendaMensal))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}

}
