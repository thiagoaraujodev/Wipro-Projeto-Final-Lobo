package com.squadlobo.api.mapper;

import org.mapstruct.Mapper;

import com.squadlobo.api.dto.ContaRequestDTO;
import com.squadlobo.api.dto.ContaResponseDTO;
import com.squadlobo.api.model.Conta;
import com.squadlobo.api.model.ContaCorrente;
import com.squadlobo.api.model.ContaEspecial;

@Mapper(componentModel = "spring")
public interface ContaMapper {

    ContaEspecial toContaEspecial(ContaRequestDTO dto);

    ContaCorrente toContaCorrente(ContaRequestDTO dto);

    ContaRequestDTO toContaRequestDto(Conta model);

    ContaResponseDTO toContaResponseDto(Conta model);
}
