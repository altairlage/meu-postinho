package com.fiap.meu_postinho_notifier.services;

import com.fiap.meu_postinho_notifier.dtos.SendCreateVisitNotification;
import com.fiap.meu_postinho_notifier.dtos.SendNotificationHttpRequestDTO;
import com.fiap.meu_postinho_notifier.dtos.SendNotificationHttpResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SendNotificationService {
    @Value("${waha.service.url}")
    private String wahaServiceUrl;

    private final Logger logger = LoggerFactory.getLogger(SendNotificationService.class);
    private final String LOG_TAG = "[SEND NOTIFICATION] - ";

    public SendNotificationHttpResponseDTO sendNotification(SendCreateVisitNotification sendCreateVisitNotification) {
        SendNotificationHttpRequestDTO requestDTO = new SendNotificationHttpRequestDTO(
                createChatId(sendCreateVisitNotification.telephone()),
                null,
                createMessage(sendCreateVisitNotification),
                true,
                false,
                "default");

        WebClient webClient = WebClient.create(wahaServiceUrl);
        return webClient.post()
                .uri("/api/sendText")
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(SendNotificationHttpResponseDTO.class)
                .block();
    }

    private String createMessage(SendCreateVisitNotification sendCreateVisitNotification) {
        return switch (sendCreateVisitNotification.visitStatus()) {
            case PENDENTE -> String.format("Olá, %s!\nSua visita foi agendada com sucesso para o dia %s, com o Agente de Saúde %s!",
                    sendCreateVisitNotification.patientName(),
                    normalizeDateAndTime(sendCreateVisitNotification.visitDate()),
                    sendCreateVisitNotification.healthAgentName());
            case CONCLUIDA -> String.format("Olá, %s!\nSua visita do dia %s, com o Agente de Saúde %s foi concluída com sucesso!",
                    sendCreateVisitNotification.patientName(),
                    sendCreateVisitNotification.visitDate(),
                    sendCreateVisitNotification.healthAgentName());
            case CANCELADA -> String.format("Olá, %s!\nSua visita do dia %s foi cancelada.\nO Agente de Saúde %s entrará em contato para verificar sua disponibilidade para uma nova visita.\nAgradecemos a paciência!",
                    sendCreateVisitNotification.patientName(),
                    sendCreateVisitNotification.visitDate(),
                    sendCreateVisitNotification.healthAgentName());
        };
    }

    private String normalizeDateAndTime(LocalDateTime visitDate) {
        String dateString = visitDate.toString();

        String[] dateStringSplit = dateString.split("T");
        String[] dateSplit = dateStringSplit[0].split("-");
        String year = dateSplit[0];
        String month = dateSplit[1];
        String day = dateSplit[2];

        String[] timeSplit = dateStringSplit[1].split(":");
        String hour = timeSplit[0];
        String minute = timeSplit[1];

        return String.format("%s/%s/%s às %sh%sm", day, month, year,hour,minute);
    }

    private String createChatId(String telephone) {
        //Testar com número de alguem do grupo inserido pelo DB da API
        return "55" + telephone.replaceAll("\\D", "") + "@c.us";
    }
}
