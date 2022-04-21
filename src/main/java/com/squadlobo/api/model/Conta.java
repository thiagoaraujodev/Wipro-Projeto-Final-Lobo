package com.squadlobo.api.model;

import com.squadlobo.api.model.exceptions.DepositoInvalidoException;
import com.squadlobo.api.model.exceptions.SaldoInsuficienteException;


import javax.persistence.*;

@MappedSuperclass
public abstract class Conta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long numeroConta;
    private Double saldo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cpf_cliente")
    private Cliente cliente;

    public Long getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Long numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public abstract void sacar(Double valor) throws SaldoInsuficienteException;

    public abstract void depositar(Double valor) throws DepositoInvalidoException;
}
