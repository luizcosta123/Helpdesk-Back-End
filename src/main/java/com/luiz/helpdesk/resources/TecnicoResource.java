package com.luiz.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luiz.helpdesk.domain.Tecnico;
import com.luiz.helpdesk.domain.dtos.TecnicoDTO;
import com.luiz.helpdesk.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

	@Autowired
	private TecnicoService service;

	/**
	 * Retorna um Tecnico a partir do id fornecido
	 *
	 * @param id
	 *
	 * @return TecnicoDTO
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		Tecnico obj = service.findById(id);
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
	}

	/**
	 * Retorna uma lista de todos os Tecnicos
	 *
	 * @return List<TecnicoDTO>
	 */
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		List<Tecnico> list = service.findAll();
		List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	/**
	 * Possibilita a criação de um novo Tecnico a partir das informações fornecidas no corpo da requisição
	 * e posteriormente retorna o Tecnico criado.
	 *
	 * @param tecnicoDTO
	 *
	 * @return TecnicoDTO
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO tecnicoDTO) {
		Tecnico newObj = service.create(tecnicoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	/**
	 * Possibilita a atualização de um Tecnico a partir das informações fornecidas no corpo da requisição
	 * e posteriormente retorna o Tecnico atualizado.
	 *
	 * @param id
	 * @param tecnicoDTO
	 *
	 * @return TecnicoDTO
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO tecnicoDTO) {
		Tecnico obj = service.update(id, tecnicoDTO);
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
	}

	/**
	 * Possibilita a exclusão de um Tecnico a partir do id fornecido
	 *
	 * @param id
	 *
	 * @return TecnicoDTO
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id) {
		service.delete(id); 
		return ResponseEntity.noContent().build();
	}

}
 

















