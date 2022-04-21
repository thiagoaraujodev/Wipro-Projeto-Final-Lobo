package com.squadlobo.api.dto;

public class ContaResponseDTO extends ContaRequestDTO {

    private String numeroConta;

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }
}
