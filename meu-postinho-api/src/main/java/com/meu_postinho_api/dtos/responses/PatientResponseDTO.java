package com.meu_postinho_api.dtos.responses;

public record PatientResponseDTO(
        Long id,
        String name,
        String telephone,
        String email
) {
}
