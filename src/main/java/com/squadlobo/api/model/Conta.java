package com.squadlobo.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Conta {

	@Id
	@Column( nullable = false ) 
	private String numeroConta;
	
	@Column( nullable = false ) 
	private String tipoConta;
	
	@Column( nullable = false ) 
	private Double limiteContaEspecial;
	
	@Column( nullable = false ) 
	private String cartaoCredito;
	
	@Column( nullable = false ) 
	private Double limiteCartaoCredito;
	
	@Column( nullable = false ) 
	private char statusConta;
	
	@ManyToOne
	@JoinColumn(name = "cliente_cpf", nullable = false)
	private Cliente cliente;

	public Conta() {
	}

	public Conta(String numeroConta, String tipoConta, Double limiteContaEspecial,
			String cartaoCredito, Double limiteCartaoCredito, char statusConta) {
		super();
		this.numeroConta = numeroConta;
		this.tipoConta = tipoConta;
		this.limiteContaEspecial = limiteContaEspecial;
		this.cartaoCredito = cartaoCredito;
		this.limiteCartaoCredito = limiteCartaoCredito;
		this.statusConta = statusConta;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public Double getLimiteContaEspecial() {
		return limiteContaEspecial;
	}

	public String getCartaoCredito() {
		return cartaoCredito;
	}

	public Double getLimiteCartaoCredito() {
		return limiteCartaoCredito;
	}

	public char getStatusConta() {
		return statusConta;
	}

	public boolean saque(String numeroConta, double saque) {
		return false;
	}

	public boolean deposito(String numeroConta, double deposito) {
		// double saldo = getSaldoConta();
		if (deposito > 0) {
			// saldo += deposito;
			// atualizaSaldo(saldo)
			return true;
		}
		return false;
	}
}
