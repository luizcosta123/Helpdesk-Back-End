package com.luiz.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luiz.helpdesk.domain.Pessoa;
import com.luiz.helpdesk.domain.Tecnico;
import com.luiz.helpdesk.domain.dtos.TecnicoDTO;
import com.luiz.helpdesk.repositories.PessoaRepository;
import com.luiz.helpdesk.repositories.TecnicoRepository;
import com.luiz.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.luiz.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Tecnico não encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(null);
		tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
		validaPorCpfEEmail(tecnicoDTO);
		Tecnico newTecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(newTecnico);
	}
 
	public Tecnico update(Integer id, @Valid TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(id);
		Tecnico oldTecnico = findById(id);
		
		if(!tecnicoDTO.getSenha().equals(oldTecnico.getSenha())) {
			tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
		}
		
		validaPorCpfEEmail(tecnicoDTO);
		oldTecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(oldTecnico);
	}

	public void delete(Integer id) {
		Tecnico tecnico = findById(id);

		if (tecnico.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico não pode ser deletado, pois possui ordens de serviço!");
		}

		tecnicoRepository.deleteById(id);
	}

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> optionalPessoa = pessoaRepository.findByCpf(objDTO.getCpf());
		if (optionalPessoa.isPresent() && optionalPessoa.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		optionalPessoa = pessoaRepository.findByEmail(objDTO.getEmail());
		if (optionalPessoa.isPresent() && optionalPessoa.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
		}
	}

}
