package com.squadlobo.api.repository;

import com.squadlobo.api.model.ContaEspecial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squadlobo.api.model.Conta;


@Repository
public interface ContaEspecialRepository extends JpaRepository<ContaEspecial, String> {

}
