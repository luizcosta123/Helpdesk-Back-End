package com.luiz.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luiz.helpdesk.domain.Cliente;
import com.luiz.helpdesk.domain.Pessoa;
import com.luiz.helpdesk.domain.dtos.ClienteDTO;
import com.luiz.helpdesk.repositories.ClienteRepository;
import com.luiz.helpdesk.repositories.PessoaRepository;
import com.luiz.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.luiz.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Cliente não encontrado! Id: " + id));
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente create(ClienteDTO clienteDTO) {
		clienteDTO.setId(null);
		clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));
		validaPorCpfEEmail(clienteDTO);
		Cliente newCliente = new Cliente(clienteDTO);
		return clienteRepository.save(newCliente);
	}

	public Cliente update(Integer id, @Valid ClienteDTO clienteDTO) {
		clienteDTO.setId(id);
		Cliente oldCliente = findById(id);
		
		if(!clienteDTO.getSenha().equals(oldCliente.getSenha())) {
			clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));
		}
		
		validaPorCpfEEmail(clienteDTO);
		oldCliente = new Cliente(clienteDTO);
		return clienteRepository.save(oldCliente);
	}

	public void delete(Integer id) {
		Cliente cliente = findById(id);

		if (cliente.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente não pode ser deletado, pois possui ordens de serviço!");
		}

		clienteRepository.deleteById(id);
	}

	private void validaPorCpfEEmail(ClienteDTO clienteDTO) {
		Optional<Pessoa> optionalPessoa = pessoaRepository.findByCpf(clienteDTO.getCpf());
		if (optionalPessoa.isPresent() && optionalPessoa.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		optionalPessoa = pessoaRepository.findByEmail(clienteDTO.getEmail());
		if (optionalPessoa.isPresent() && optionalPessoa.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
		}
	}

}
