package com.squadlobo.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squadlobo.api.model.Cliente;
import com.squadlobo.api.repository.ClienteRepository;
import com.squadlobo.api.service.exceptions.NotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

<<<<<<< HEAD
    public Cliente buscarCpf(String cpf) {
        return clienteRepository.findById(cpf)
                .orElseThrow(() -> new NotFoundException("CPF: " + cpf + " não encontado!"));
    }

=======
>>>>>>> e59c940e6d0e6507a41354154b34cae92b8856c5
//    public Cliente create(Cliente obj) {
//        return clienteRepository.save(obj);
//    }
    
    public Cliente atualizarCliente(String cpf, Cliente obj) {
    	Cliente cliente = buscarCpf(cpf);
    	cliente.setNome(obj.getNome());
    	cliente.setDataNascimento(obj.getDataNascimento());
    	cliente.setTelefone(obj.getTelefone());
    	cliente.setRendaMensal(obj.getRendaMensal());
        return clienteRepository.save(obj);
    }
    
    public Cliente deletar(String cpf) {
    	return clienteRepository.getById(cpf);
    }
    
<<<<<<< HEAD
   
=======
    public  Cliente alterar(String cpf, Cliente obj) {
    	Cliente cliente =  buscarCpf(cpf);
    	cliente.setNome(obj.getNome());
    	cliente.setDataNascimento(obj.getDataNascimento());
    	cliente.setTelefone(obj.getTelefone());
    	cliente.setRendaMensal(obj.getRendaMensal());
    	return clienteRepository.save(cliente);
    }

    public Cliente buscarCpf(String cpf) {
        return clienteRepository.findById(cpf)
                .orElseThrow(() -> new NotFoundException("CPF: " + cpf + " não encontado!"));
    }
>>>>>>> e59c940e6d0e6507a41354154b34cae92b8856c5
}
