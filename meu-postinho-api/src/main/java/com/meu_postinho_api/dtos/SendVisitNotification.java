package com.meu_postinho_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meu_postinho_api.enums.VisitStatusEnum;

import java.time.LocalDateTime;

public record SendVisitNotification(
        @JsonProperty("nomePaciente") String patientName,
        @JsonProperty("telefone") String telephone,
        @JsonProperty("nomeAgenteSaude") String healthAgentName,
        @JsonProperty("dataVisita") LocalDateTime visitDate,
        @JsonProperty("statusVisita") VisitStatusEnum visitStatus,
        @JsonProperty("motivoVisita") String visitReason,
        @JsonProperty("observacoesVisita") String visitObservations
) {
}
