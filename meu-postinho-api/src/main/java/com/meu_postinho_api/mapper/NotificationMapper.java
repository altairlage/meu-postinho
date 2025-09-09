package com.meu_postinho_api.mapper;

import com.meu_postinho_api.dtos.SendCreateVisitNotification;
import com.meu_postinho_api.dtos.SendUpdateVisitNotification;
import com.meu_postinho_api.entities.Visit;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public SendCreateVisitNotification toCreateVisitNotification(Visit visit) {
        return new SendCreateVisitNotification(
                visit.getId()
        );
    }

    public SendUpdateVisitNotification toUpdateVisitNotification(Visit visit) {
        return new SendUpdateVisitNotification(
                visit.getId()
        );
    }
}
