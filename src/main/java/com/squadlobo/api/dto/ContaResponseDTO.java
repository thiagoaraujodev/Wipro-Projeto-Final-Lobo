package com.squadlobo.api.dto;

public class ContaResponseDTO extends ContaRequestDTO {

    private Long numeroConta;

    public Long getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Long numeroConta) {
        this.numeroConta = numeroConta;
    }
}
