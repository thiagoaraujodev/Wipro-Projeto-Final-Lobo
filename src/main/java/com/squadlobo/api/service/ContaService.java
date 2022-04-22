package com.squadlobo.api.service;

import java.util.Optional;
import java.util.Random;

import com.squadlobo.api.service.exceptions.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.squadlobo.api.dto.ContaRequestDTO;
import com.squadlobo.api.dto.MovimentacaoDTO;
import com.squadlobo.api.mapper.ContaMapper;
import com.squadlobo.api.model.Conta;
import com.squadlobo.api.model.ContaCorrente;
import com.squadlobo.api.model.ContaEspecial;
import com.squadlobo.api.model.TipoMovimentacao;
import com.squadlobo.api.repository.ClienteRepository;
import com.squadlobo.api.repository.ContaCorrenteRepository;
import com.squadlobo.api.repository.ContaEspecialRepository;
import com.squadlobo.api.repository.ContaRepository;
import com.squadlobo.api.service.exceptions.NotFoundException;

@Service
public class ContaService {

    Random random = new Random();

    @Value("${wipro.banco.teto.conta.especial}")
    private Double tetoContaEspecial;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MovimentacaoService movimentacaoService;

    @Autowired
    private ContaEspecialRepository contaEspecialRepository;

    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    private ContaMapper mapper;

    public Conta create(ContaRequestDTO contaDTO) {
        validarCpf(contaDTO.getCliente().getCpf());

        Conta novaConta = null;
        if (contaDTO.getCliente().getRendaMensal() >= tetoContaEspecial) {
            ContaEspecial contaEspecial = mapper.toContaEspecial(contaDTO);
            contaEspecial.setLimiteContaEspecial(gerarLimiteContaEspecial
                    (contaDTO.getCliente().getRendaMensal()));
            contaEspecial.setLimiteUtilizado(0.0);
            novaConta = contaEspecial;
            novaConta.setNumeroConta(gerarNumeroConta(contaEspecialRepository));
        } else {
            novaConta = mapper.toContaCorrente(contaDTO);
            novaConta.setNumeroConta(gerarNumeroConta(contaCorrenteRepository));
        }
        novaConta.setSaldo(0d);
        novaConta.setCartaoCredito(gerarNumeroCartao());
        novaConta.setLimiteCartaoCredito(gerarLimiteCartao(contaDTO.getCliente().getRendaMensal()));
        contaRepository.save(novaConta);
        return novaConta;
    }

    private void validarCpf(String cpf) {
        if (clienteRepository.countByCpf(cpf) >= 1) {
            throw new NotFoundException("CPF já cadastrado!");
        }
    }

    private String gerarNumeroConta(JpaRepository repository) {
        String numeracao = "";
        for (int i = 0; i < 5; i++) {
            numeracao += Integer.toString(random.nextInt(9));
        }
        if (repository.existsById(numeracao)) {
            numeracao = gerarNumeroConta(repository);
        }
        return numeracao;
    }

    private Double gerarLimiteContaEspecial(Double renda) {
        Double limite = 0d;
        if (renda >= tetoContaEspecial) {
            limite = renda * 0.1;
        }
        return limite;
    }

    private String gerarNumeroCartao() {

        String numeracao = "55";
        for (int i = 0; i < 14; i++) {
            numeracao += Integer.toString(random.nextInt(9));
            if (i == 1 || i == 5 || i == 9) {
                numeracao += " ";
            }
        }
        return numeracao;
    }

    public Double gerarLimiteCartao(Double renda) {
        return renda * 0.30;
    }

    public void sacar(String numeroConta, MovimentacaoDTO saque) {
        Conta conta = recuperarConta(numeroConta);
        conta.sacar(saque.getValor());
        contaRepository.save(conta);
        movimentacaoService.criarMovimentacao(conta, TipoMovimentacao.SAQUE, saque.getValor());
    }

    public void depositar(String numeroConta, MovimentacaoDTO deposito) {
        Conta conta = recuperarConta(numeroConta);
        conta.depositar(deposito.getValor());
        contaRepository.save(conta);
        movimentacaoService.criarMovimentacao(conta, TipoMovimentacao.DEPOSITO, deposito.getValor());
    }

    public Conta recuperarConta(String numeroConta) {
        Conta conta = null;
        Optional<ContaCorrente> contaCorrente = contaCorrenteRepository.findById(numeroConta);
        if (contaCorrente.isPresent()) {
            conta = contaCorrente.get();
        } else {
            Optional<ContaEspecial> contaEspecial = contaEspecialRepository.findById(numeroConta);
            if (contaEspecial.isPresent()) {
                conta = contaEspecial.get();
            } else {
                throw new IllegalArgumentException("Conta inexistente");
            }
        }
        return conta;
    }
}
