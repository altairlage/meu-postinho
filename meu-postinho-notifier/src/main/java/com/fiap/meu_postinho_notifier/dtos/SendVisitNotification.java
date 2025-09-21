package com.fiap.meu_postinho_notifier.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.meu_postinho_notifier.enums.VisitStatusEnum;

import java.time.LocalDateTime;

public record SendVisitNotification(
        @JsonProperty("nomePaciente") String patientName,
        @JsonProperty("telefone") String telephone,
        @JsonProperty("nomeAgenteSaude") String healthAgentName,
        @JsonProperty("dataVisita") LocalDateTime visitDate,
        @JsonProperty("statusVisita") VisitStatusEnum visitStatus,
        @JsonProperty("motivoVisita") String reason,
        @JsonProperty("observacoesVisita") String observations
) {
}