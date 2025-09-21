package com.meu_postinho_api.mapper;

import com.meu_postinho_api.dtos.SendVisitNotification;
import com.meu_postinho_api.entities.Visit;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public SendVisitNotification toVisitNotification(Visit visit) {
        return new SendVisitNotification(
                visit.getPatient().getName(),
                visit.getPatient().getTelephone(),
                visit.getHealthAgent().getName(),
                visit.getVisitDate(),
                visit.getStatus(),
                visit.getReason(),
                visit.getObservations()
        );
    }
}
