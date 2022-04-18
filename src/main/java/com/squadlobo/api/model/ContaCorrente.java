package com.squadlobo.api.model;

public class ContaCorrente extends Conta {

	public ContaCorrente() {

	}

	public ContaCorrente(String numeroConta, String tipoConta, Double limiteContaEspecial, String cartaoCredito,
			Double limiteCartaoCredito, char statusConta) {
		super(numeroConta, tipoConta, limiteContaEspecial, cartaoCredito, limiteCartaoCredito, statusConta);
	}

	@Override
	public boolean saque(String numeroConta, double saque) {
		double saldo = 0;//corrigir

		if (saque > 0.0 && saque <= saldo) {
			return true;
		}
		return false;
	}
}
