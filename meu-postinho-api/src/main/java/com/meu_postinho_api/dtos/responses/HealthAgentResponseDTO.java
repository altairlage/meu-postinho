package com.meu_postinho_api.dtos.responses;

public record HealthAgentResponseDTO(
        Long id,
        String name,
        String telephone,
        String registerNumber,
        String healthUnity
) {
}
