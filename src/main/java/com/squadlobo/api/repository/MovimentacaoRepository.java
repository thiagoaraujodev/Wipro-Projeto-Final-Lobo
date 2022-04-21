package com.squadlobo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squadlobo.api.model.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
}
