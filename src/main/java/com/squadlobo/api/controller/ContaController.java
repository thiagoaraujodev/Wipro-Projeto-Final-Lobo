package com.squadlobo.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.squadlobo.api.dto.ContaRequestDTO;
import com.squadlobo.api.dto.ContaResponseDTO;
import com.squadlobo.api.mapper.ContaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<List<Conta>> GetAll() {
		List<Conta> list = contaService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/{numeroConta}")
	public ResponseEntity<Conta> GetById(@PathVariable @Valid Long numeroConta) {
		Conta obj = contaService.findById(numeroConta);
		return ResponseEntity.ok(obj);
	}

	@PostMapping("/criar")
	public ContaResponseDTO criar(@RequestBody @Valid ContaRequestDTO conta) {
//		return ResponseEntity.status(HttpStatus.CREATED).body(contaService.create(conta));
		Conta newObj = contaService.create(conta);
		return mapper.toContaResponseDto(newObj);
	}

}
