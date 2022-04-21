package com.squadlobo.api.repository;

import com.squadlobo.api.model.ContaCorrente;
import com.squadlobo.api.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
}
