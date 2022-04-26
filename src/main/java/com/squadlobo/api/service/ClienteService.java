package com.squadlobo.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squadlobo.api.model.Cliente;
import com.squadlobo.api.repository.ClienteRepository;
import com.squadlobo.api.service.exceptions.ObjetoNaoEncontradoException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarCpf(String cpf) {
        return clienteRepository.findById(cpf)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("CPF não encontado!"));
    }

    public Cliente atualizarCliente(String cpf, Cliente obj) {
        Cliente cliente = buscarCpf(cpf);
        cliente.setNome(obj.getNome());
        cliente.setDataNascimento(obj.getDataNascimento());
        cliente.setTelefone(obj.getTelefone());
        cliente.setRendaMensal(obj.getRendaMensal());
        return clienteRepository.save(obj);
    }

}