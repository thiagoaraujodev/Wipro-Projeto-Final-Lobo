package com.squadlobo.api.model;

import com.squadlobo.api.model.exceptions.DepositoInvalidoException;
import com.squadlobo.api.model.exceptions.SaldoInsuficienteException;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ContaCorrente extends Conta {

    @OneToMany(mappedBy = "contaCorrente")
    private List<MovimentacaoContaCorrente> movimentacoes;

    @Override
    public void sacar(Double valor) throws SaldoInsuficienteException {
        if (getSaldo() >= valor) {
            Double novoSaldo = getSaldo() - valor;
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
