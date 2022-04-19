package com.squadlobo.api.model;

@SuppressWarnings("serial")
public class ContaEspecial extends Conta {
	
	public double valorLimite;
	
	
	//private static final long serialVersionUID = 1L;

	public ContaEspecial() {

	}

	public ContaEspecial(String numeroConta, String tipoConta, Double limiteContaEspecial, String cartaoCredito,
			Double limiteCartaoCredito, char statusConta, Cliente cliente) {
		super(numeroConta, tipoConta, limiteContaEspecial, cartaoCredito, limiteCartaoCredito, statusConta, cliente);
	}

	
}
