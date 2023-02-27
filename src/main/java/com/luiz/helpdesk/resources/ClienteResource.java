package com.luiz.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luiz.helpdesk.domain.Cliente;
import com.luiz.helpdesk.domain.dtos.ClienteDTO;
import com.luiz.helpdesk.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	/**
	 * Retorna um Cliente a partir do id fornecido
	 *
	 * @param id
	 *
	 * @return ClienteDTO
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		Cliente obj = service.findById(id);
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}

	/**
	 * Retorna uma lista de todos os Clientes
	 *
	 * @return List<ClienteDTO>
	 */
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	/**
	 * Possibilita a criação de um novo Cliente a partir das informações fornecidas no corpo da requisição
	 * e posteriormente retorna o Cliente criado.
	 *
	 * @param clienteDTO
	 *
	 * @return ClienteDTO
	 */
	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO clienteDTO) {
		Cliente newObj = service.create(clienteDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	/**
	 * Possibilita a atualização de um Cliente a partir das informações fornecidas no corpo da requisição
	 * e posteriormente retorna o Cliente atualizado.
	 *
	 * @param id
	 * @param clienteDTO
	 *
	 * @return ClienteDTO
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO clienteDTO) {
		Cliente obj = service.update(id, clienteDTO);
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}

	/**
	 * Possibilita a exclusão de um Cliente a partir do id fornecido
	 *
	 * @param id
	 *
	 * @return ClienteDTO
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id) {
		service.delete(id); 
		return ResponseEntity.noContent().build();
	}

}
