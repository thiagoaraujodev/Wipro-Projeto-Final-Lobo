package com.squadlobo.api.model;

import javax.persistence.*;

@Entity
public class MovimentacaoContaEspecial extends Movimentacao {

    @ManyToOne
    @JoinColumn(name = "numero_conta")
    private ContaEspecial contaEspecial;

    public ContaEspecial getContaEspecial() {
        return contaEspecial;
    }

    public void setContaEspecial(ContaEspecial contaEspecial) {
        this.contaEspecial = contaEspecial;
    }
}
