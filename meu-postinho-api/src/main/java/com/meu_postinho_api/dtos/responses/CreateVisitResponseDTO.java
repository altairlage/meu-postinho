package com.meu_postinho_api.dtos.responses;

import com.meu_postinho_api.entities.HealthAgent;
import com.meu_postinho_api.entities.Patient;
import com.meu_postinho_api.enums.VisitStatusEnum;

import java.time.LocalDateTime;

public record CreateVisitResponseDTO(
        Long id,
        HealthAgentResponseDTO healthAgentResponseDTO,
        PatientResponseDTO patientResponseDTO,
        LocalDateTime visitDate,
        String reason,
        String observations,
        VisitStatusEnum status
) {
}
