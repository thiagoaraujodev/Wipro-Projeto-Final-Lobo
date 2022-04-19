package com.squadlobo.api.model;

public class ContaCorrente extends Conta {
	
	private static final long serialVersionUID = 1L;

	public ContaCorrente() {

	}

	public ContaCorrente(String numeroConta, String tipoConta, Double limiteContaEspecial, String cartaoCredito,
			Double limiteCartaoCredito, char statusConta, Cliente cliente) {
		super(numeroConta, tipoConta, limiteContaEspecial, cartaoCredito, limiteCartaoCredito, statusConta, cliente);
	}

	@Override
	public boolean saque(String numeroConta, double saque) {
		double saldo = 0;// corrigir

		if (saque > 0.0 && saque <= saldo) {
			return true;
		}
		return false;
	}

}
