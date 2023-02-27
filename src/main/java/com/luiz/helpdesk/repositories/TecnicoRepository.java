package com.luiz.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luiz.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
