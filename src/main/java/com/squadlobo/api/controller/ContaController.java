package com.squadlobo.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.squadlobo.api.dto.ContaRequestDTO;
import com.squadlobo.api.dto.ContaResponseDTO;
import com.squadlobo.api.dto.MovimentacaoDTO;
import com.squadlobo.api.mapper.ContaMapper;
import com.squadlobo.api.model.Conta;
import com.squadlobo.api.service.ContaService;

@RestController
@RequestMapping("/contas")
@CrossOrigin("*")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@Autowired
	private ContaMapper mapper;

	@GetMapping
	public ResponseEntity<List<Conta>> listarContas() {
		List<Conta> list = null;
		return ResponseEntity.ok().body(list);
	}

	@PatchMapping("/saque/{numeroConta}")
	public ResponseEntity<Void> sacar(@PathVariable @Valid String numeroConta, @RequestBody @Valid MovimentacaoDTO movimentacao) {
		contaService.sacar(numeroConta, movimentacao);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/deposito/{numeroConta}")
	public ResponseEntity<Void> depositar(@PathVariable @Valid String numeroConta, @RequestBody @Valid MovimentacaoDTO movimentacao) {
		contaService.depositar(numeroConta, movimentacao);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{numeroConta}")
	public ResponseEntity<ContaResponseDTO> recuperarConta(@PathVariable @Valid String numeroConta) {
		Conta obj = contaService.recuperarConta(numeroConta);
		return ResponseEntity.ok(mapper.toContaResponseDto(obj));
	}

	//criar a conta
	@PostMapping
	public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody @Valid ContaRequestDTO conta) {
		Conta contaNova = contaService.create(conta);
		ContaResponseDTO response = mapper.toContaResponseDto(contaNova);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{numeroConta}").buildAndExpand(contaNova.getNumeroConta())
				.toUri();
		return ResponseEntity.created(uri).body(response);
	}

}
