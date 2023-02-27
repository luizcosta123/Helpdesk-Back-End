package com.luiz.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luiz.helpdesk.domain.Chamado;
import com.luiz.helpdesk.domain.Cliente;
import com.luiz.helpdesk.domain.Tecnico;
import com.luiz.helpdesk.domain.dtos.ChamadoDTO;
import com.luiz.helpdesk.domain.enums.Prioridade;
import com.luiz.helpdesk.domain.enums.Status;
import com.luiz.helpdesk.repositories.ChamadoRepository;
import com.luiz.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;

	public Chamado findById(Integer id) {
		Optional<Chamado> chamadoOptional = repository.findById(id);
		return chamadoOptional.orElseThrow(() -> new ObjectnotFoundException("Chamado não encontrado! Id: " + id));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}

	public Chamado create(ChamadoDTO chamadoDTO) {
		return repository.save(newChamado(chamadoDTO));
	}

	public Chamado update(Integer id, @Valid ChamadoDTO chamadoDTO) {
		chamadoDTO.setId(id);

		Chamado oldChamado = findById(id);
		oldChamado = newChamado(chamadoDTO);

		return repository.save(oldChamado);
	}

	private Chamado newChamado(ChamadoDTO chamadoDTO) {
		Tecnico tecnico = tecnicoService.findById(chamadoDTO.getTecnico());
		Cliente cliente = clienteService.findById(chamadoDTO.getCliente());
		
		Chamado chamado = new Chamado();
		if(chamadoDTO.getId() != null) {
			chamado.setId(chamadoDTO.getId());
		}
		
		if(chamadoDTO.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(chamadoDTO.getPrioridade()));
		chamado.setStatus(Status.toEnum(chamadoDTO.getStatus()));
		chamado.setTitulo(chamadoDTO.getTitulo());
		chamado.setObservacoes(chamadoDTO.getObservacoes());
		return chamado;
	}

}
















