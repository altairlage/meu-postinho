package com.fiap.meu_postinho_notifier.services;

import com.fiap.meu_postinho_notifier.dtos.SendNotificationDTO;
import com.fiap.meu_postinho_notifier.dtos.SendNotificationHttpRequestDTO;
import com.fiap.meu_postinho_notifier.dtos.SendNotificationHttpResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SendNotificationService {
    public SendNotificationHttpResponseDTO sendNotification(SendNotificationDTO sendNotificationDTO) {
        SendNotificationHttpRequestDTO requestDTO = new SendNotificationHttpRequestDTO(
//                sendNotificationDTO.patientPhoneNumber() + "@c.us",
//                "553591252515@c.us",
                "5511953910857@c.us",
                null,
//                createMessage(sendNotificationDTO),
                "Teste envio pela nossa aplicação",
                true,
                false,
                "default");

        WebClient webClient = WebClient.create("localhost:3000");
        return webClient.post()
                .uri("/api/sendText")
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(SendNotificationHttpResponseDTO.class)
                .block();
    }

    private String createMessage(SendNotificationDTO sendNotificationDTO) {
        return switch (sendNotificationDTO.visitationStatus()) {
            case PENDENTE -> String.format("Olá, %s!\nSua visita foi agendada com sucesso para o dia %s, com o Agente de Saúde %s",
                    sendNotificationDTO.patientName(),
                    sendNotificationDTO.visitationDate(),
                    sendNotificationDTO.healthAgentName());
            case CONCLUIDA -> String.format("Olá, %s!\nSua visita do dia %s, com o Agente de Saúde %s foi concluída com sucesso!",
                    sendNotificationDTO.patientName(),
                    sendNotificationDTO.visitationDate(),
                    sendNotificationDTO.healthAgentName());
            case CANCELADA -> String.format("Olá, %s!\nSua visita do dia %s foi cancelada.\nO Agente de Saúde %s entrará em contato para verificar sua disponibilidade para uma nova visita.\nAgradecemos a paciência!",
                    sendNotificationDTO.patientName(),
                    sendNotificationDTO.visitationDate(),
                    sendNotificationDTO.healthAgentName());
        };
    }
}
