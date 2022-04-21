package com.squadlobo.api.service;

import java.util.List;

import com.squadlobo.api.dto.ContaRequestDTO;
import com.squadlobo.api.mapper.ContaMapper;
import com.squadlobo.api.model.ContaEspecial;
import com.squadlobo.api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.squadlobo.api.model.Conta;
import com.squadlobo.api.repository.ContaRepository;
import com.squadlobo.api.service.exceptions.NotFoundException;

@Service
public class ContaService {

    @Value("${wipro.banco.teto.conta.especial}")
    private Double tetoContaEspecial;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ContaMapper mapper;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    public Conta findById(Long numeroConta) {
        return contaRepository.findById(numeroConta)
                .orElseThrow(() -> new NotFoundException("Conta: " + numeroConta + " não encontada!"));
    }

    public Conta create(ContaRequestDTO conta) {
        Conta contaNova = null;
        validarCpf(conta.getCliente().getCpf());
        if (conta.getCliente().getRendaMensal() > tetoContaEspecial) {
            ContaEspecial contaEspecial = mapper.toContaEspecial(conta);
            contaEspecial.setLimiteContaEspecial(100.00);
            contaNova = contaEspecial;
        } else {
            contaNova = mapper.toContaCorrente(conta);
        }
        contaNova.setSaldo(0d);

        contaNova.setNumeroConta(10481L);
        contaRepository.save(contaNova);
        return contaNova;
    }

    public void validarCpf(String cpf) {
        if (clienteRepository.countByCpf(cpf) >= 1) {
            throw new NotFoundException("CPF já cadastrado!");
        }
    }
}
