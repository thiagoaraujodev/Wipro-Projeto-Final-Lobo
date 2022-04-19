package com.squadlobo.api.model;

public class ContaEspecial extends Conta {

	private static final long serialVersionUID = 1L;

	public ContaEspecial() {

	}

	public ContaEspecial(String numeroConta, String tipoConta, Double limiteContaEspecial, String cartaoCredito,
			Double limiteCartaoCredito, char statusConta, Cliente cliente) {
		super(numeroConta, tipoConta, limiteContaEspecial, cartaoCredito, limiteCartaoCredito, statusConta, cliente);
	}

	@Override
	public boolean saque(String numeroConta, double saque) {
		double saldo = 0;// corrigir
		double limiteEspecial = getLimiteContaEspecial();

		if (saque > 0.0 && saque <= (saldo + limiteEspecial)) {
			return true;
		}
		return false;
	}
}
