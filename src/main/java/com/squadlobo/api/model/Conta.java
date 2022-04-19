package com.squadlobo.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String numeroConta;
	private String tipoConta;
	private Double limiteContaEspecial;
	private String cartaoCredito;
	private Double limiteCartaoCredito;
	private char statusConta;

	@OneToOne
	@JoinColumn(name = "cpf_cliente")
	private Cliente cliente;

	public Conta() {
	}

	public Conta(String numeroConta, String tipoConta, Double limiteContaEspecial, String cartaoCredito,
			Double limiteCartaoCredito, char statusConta, Cliente cliente) {
		super();
		this.numeroConta = numeroConta;
		this.tipoConta = tipoConta;
		this.limiteContaEspecial = limiteContaEspecial;
		this.cartaoCredito = cartaoCredito;
		this.limiteCartaoCredito = limiteCartaoCredito;
		this.statusConta = statusConta;
		this.cliente = cliente;
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

	public Cliente getCliente() {
		return cliente;
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
