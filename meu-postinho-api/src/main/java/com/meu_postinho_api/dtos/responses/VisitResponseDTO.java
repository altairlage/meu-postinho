package com.meu_postinho_api.dtos.responses;

import com.meu_postinho_api.enums.VisitStatusEnum;

import java.time.LocalDateTime;

public record VisitResponseDTO(
        Long id,
        LocalDateTime visitDate,
        String reason,
        String observations,
        VisitStatusEnum status,
        String patientName,
        String healthAgentName
) {
}
