package com.squadlobo.api.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.squadlobo.api.model.exceptions.DepositoInvalidoException;
import com.squadlobo.api.model.exceptions.SaldoInsuficienteException;

@Entity
public class ContaCorrente extends Conta {

	@JsonIgnore
	@OneToMany(mappedBy = "contaCorrente")
	private List<MovimentacaoContaCorrente> movimentacoes;

	@Override
	public void sacar(Double valor) throws SaldoInsuficienteException {
		if (getSaldo() >= valor) {
			// calcula o valor do novo saldo
			Double novoSaldo = getSaldo() - valor;
			// atualiza o saldo
			setSaldo(novoSaldo);
		} else {
			throw new SaldoInsuficienteException();
		}
	}

	@Override
	public void depositar(Double valor) throws DepositoInvalidoException {
		setSaldo(getSaldo() + valor);
	}

	public List<MovimentacaoContaCorrente> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<MovimentacaoContaCorrente> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}
}
