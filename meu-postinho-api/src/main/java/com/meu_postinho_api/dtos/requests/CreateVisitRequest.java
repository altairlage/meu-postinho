package com.meu_postinho_api.dtos.requests;

import com.meu_postinho_api.enums.VisitStatusEnum;

import java.time.LocalDateTime;

public record CreateVisitRequest(
    Long healthAgentId,
    Long patientId,
    LocalDateTime visitDate,
    String reason,
    String observations,
    VisitStatusEnum status
) {
}
