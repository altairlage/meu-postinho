package com.meu_postinho_api.dtos.responses;

import com.meu_postinho_api.enums.VisitStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FinishVisitResponseDTO(
        Long id,
        HealthAgentResponseDTO healthAgentResponseDTO,
        PatientResponseDTO patientResponseDTO,
        LocalDateTime visitDate,
        String reason,
        String observations,
        VisitStatusEnum status,
        BigDecimal finLatiude,
        BigDecimal finLongitude
) {
}
