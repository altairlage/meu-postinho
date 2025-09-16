package com.fiap.meu_postinho_notifier.dtos;

import com.fiap.meu_postinho_notifier.enums.VisitationStatusEnum;

public record SendNotificationDTO(
        String patientPhoneNumber,
        String patientName,
        String healthAgentName,
        String visitationDate,
        VisitationStatusEnum visitationStatus
) {}
