package com.squadlobo.api.model;

public class ContaEspecial extends Conta {

	public ContaEspecial() {

	}

	public ContaEspecial(String numeroConta, String tipoConta, Double limiteContaEspecial, String cartaoCredito,
			Double limiteCartaoCredito, char statusConta) {
		super(numeroConta, tipoConta, limiteContaEspecial, cartaoCredito, limiteCartaoCredito, statusConta);
	}

	@Override
	public boolean saque(String numeroConta, double saque) {
		double saldo = 0;//corrigir
		double limiteEspecial = getLimiteContaEspecial();

		if (saque > 0.0 && saque <= (saldo + limiteEspecial)) {
			return true;
		}
		return false;
	}
}
