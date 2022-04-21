package com.squadlobo.api.repository;

import com.squadlobo.api.model.ContaCorrente;
import com.squadlobo.api.model.ContaEspecial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, String> {

}
