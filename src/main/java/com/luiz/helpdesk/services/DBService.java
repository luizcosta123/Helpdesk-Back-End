package com.luiz.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luiz.helpdesk.domain.Chamado;
import com.luiz.helpdesk.domain.Cliente;
import com.luiz.helpdesk.domain.Tecnico;
import com.luiz.helpdesk.domain.enums.Perfil;
import com.luiz.helpdesk.domain.enums.Prioridade;
import com.luiz.helpdesk.domain.enums.Status;
import com.luiz.helpdesk.repositories.ChamadoRepository;
import com.luiz.helpdesk.repositories.PessoaRepository;

@Service
public class DBService {

	@Autowired
	private ChamadoRepository chamadoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instanciaDB() {

		Tecnico t1 = new Tecnico(null, "Luiz", "50194040801", "luizcosta12322@yahoo.com", encoder.encode("123123123"));
		Tecnico t2 = new Tecnico(null, "Tadeo", "51682380092", "Tadeo41@gmail.com", encoder.encode("123123123"));
		Tecnico t3 = new Tecnico(null, "Jeremias", "37182154030", "jeremias23@gmail.com", encoder.encode("123123123"));
		Tecnico t4 = new Tecnico(null, "Kleiton", "47597704020", "kleiton51@gmail.com", encoder.encode("123123123"));
		Tecnico t5 = new Tecnico(null, "Kléber", "31700496077", "kleber23@gmail.com", encoder.encode("123123123"));
		Tecnico t6 = new Tecnico(null, "Carlos", "61837466068", "carlos234@gmail.com", encoder.encode("123123123"));
		Tecnico t7 = new Tecnico(null, "James", "81136696040", "james123@gmail.com", encoder.encode("123123123"));
		Tecnico t8 = new Tecnico(null, "João", "23873048027", "joao512@yahoo.com", encoder.encode("123123123"));

		t1.addPerfil(Perfil.ADMIN);
		t2.addPerfil(Perfil.ADMIN);
		t3.addPerfil(Perfil.ADMIN);

		Cliente cl1 = new Cliente(null, "Joana", "30743287037", "joana@mail.com", encoder.encode("142141142"));
		Cliente cl2 = new Cliente(null, "Jefferson", "81165528053", "jefferson@mail.com", encoder.encode("142141142"));
		Cliente cl3 = new Cliente(null, "Cristiano", "04810871096", "cristiano@mail.com", encoder.encode("142141142"));
		Cliente cl4 = new Cliente(null, "Maria", "49931470070", "maria@mail.com", encoder.encode("142141142"));
		Cliente cl5 = new Cliente(null, "Cássia", "03187680038", "cassia@mail.com", encoder.encode("142141142"));
		Cliente cl6 = new Cliente(null, "Carla", "42697135006", "carla@mail.com", encoder.encode("142141142"));
 
		Chamado c1 = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "Chamado para ajuste de ... I", "OBS: I", t1, cl1);
		Chamado c2 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado para correção de ... II", "OBS: II", t2, cl2);
		Chamado c3 = new Chamado(null, Prioridade.ALTA, Status.ENCERRADO, "Chamado para análise ... III", "OBS: III", t3, cl3);
		Chamado c4 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado para ajuste de ... IV", "OBS: IV", t4, cl4);
		Chamado c5 = new Chamado(null, Prioridade.BAIXA, Status.ANDAMENTO, "Chamado para análise ... V", "OBS: V", t5, cl5);
		Chamado c6 = new Chamado(null, Prioridade.MEDIA, Status.ENCERRADO, "Chamado para ajuste de ... VI", "OBS: VI", t6, cl5);

		pessoaRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, cl1, cl2, cl3, cl4, cl5, cl6));
		chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6));
	}
}
