package com.squadlobo.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.squadlobo.api.model.Cliente;
import com.squadlobo.api.service.ClienteService;

@RestController
@RequestMapping("/cliente")
@CrossOrigin("*")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public ResponseEntity<List<Cliente>> listarClientes() {
		List<Cliente> list = clienteService.listarClientes();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<Cliente> buscarCpf(@PathVariable @Valid String cpf) {
		Cliente obj = clienteService.buscarCpf(cpf);
		return ResponseEntity.ok().body(obj);
	}

	@PutMapping("/{cpf}")
	public ResponseEntity<Cliente> atualizarCliente(@PathVariable @Valid String cpf, @RequestBody Cliente cliente) {
		Cliente obj = clienteService.atualizarCliente(cpf, cliente);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(obj);
	}

	//@DeleteMapping("/{cpf}")/*mapeia a URL*/
	//@ResponseBody /*descrição da resposta*/
	//public ResponseEntity<Cliente> Delete(@PathVariable String cpf) {/*recebe os dados para deletar*/
	//	clienteService.delete(cpf);
	//	return ResponseEntity.noContent().build();
	//}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}



}
