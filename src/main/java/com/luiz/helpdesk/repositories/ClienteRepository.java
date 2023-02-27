package com.luiz.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luiz.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
