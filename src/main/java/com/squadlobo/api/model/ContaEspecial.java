package com.squadlobo.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.squadlobo.api.model.exceptions.DepositoInvalidoException;
import com.squadlobo.api.model.exceptions.SaldoInsuficienteException;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@SQLDelete(sql = "UPDATE conta_especial SET ativo = false WHERE numero_conta = ?")
@Where(clause = "ativo = true")
@Entity
public class ContaEspecial extends Conta {

	private Double limiteContaEspecial;
	private Double limiteUtilizado;

	@JsonIgnore
	@OneToMany(mappedBy = "contaEspecial", cascade = CascadeType.ALL)
	private List<MovimentacaoContaEspecial> movimentacoes;

	public Double getLimiteContaEspecial() {
		return limiteContaEspecial;
	}

	public void setLimiteContaEspecial(Double limiteContaEspecial) {
		this.limiteContaEspecial = limiteContaEspecial;
	}

	public Double getLimiteUtilizado() {
		return limiteUtilizado;
	}

	public void setLimiteUtilizado(Double limiteUtilizado) {
		this.limiteUtilizado = limiteUtilizado;
	}

	public List<MovimentacaoContaEspecial> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<MovimentacaoContaEspecial> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	@Override
	public void sacar(Double valor) throws SaldoInsuficienteException {
		if (valor <= getSaldo()) {
			setSaldo(getSaldo() - valor);
		} else if (valor <= (getSaldo() + (limiteContaEspecial - limiteUtilizado))) {
			limiteUtilizado += (valor - getSaldo());
			setSaldo(getSaldo() - limiteUtilizado);
		} else {
			throw new SaldoInsuficienteException();
		}
	}

	@Override
	public void depositar(Double valor) throws DepositoInvalidoException {
		// atualiza o valor que ira para o saldo e o que ira
		// reconstituir o limite de credito entra no if se
		// o cliente estiver utilizando o limite de credito
		if (limiteUtilizado > 0) {
			// se o valor depositado for menor ou igual ao limite utilizado
			// nenhum valor ira compor o saldo [valor = 0] e todo o valor
			// depositado serah removido do limite utilizado
			if (valor <= limiteUtilizado) {
				limiteUtilizado -= valor;
				valor = 0d;
			} else {
				// remove do valor depositado a quantidade que estava sendo
				// utilizada no limite e zera o limite utilizado
				valor -= limiteUtilizado;
				limiteUtilizado = 0d;
			}
		}
		setSaldo(getSaldo() + valor);
	}

}
