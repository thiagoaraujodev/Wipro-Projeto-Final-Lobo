package com.squadlobo.api.model;

import com.squadlobo.api.model.exceptions.DepositoInvalidoException;
import com.squadlobo.api.model.exceptions.SaldoInsuficienteException;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ContaEspecial extends Conta {

    private static final long serialVersionUID = 1L;

    private Double limiteContaEspecial;
    private Double limiteUtilizado;

    @OneToMany(mappedBy = "contaEspecial")
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

    }

    @Override
    public void depositar(Double valor) throws DepositoInvalidoException {

    }
}
