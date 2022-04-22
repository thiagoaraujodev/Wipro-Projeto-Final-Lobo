package com.squadlobo.api.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MovimentacaoContaCorrente extends Movimentacao {

	@ManyToOne
	@JoinColumn(name = "numero_conta")
	private ContaCorrente contaCorrente;

	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}
}
