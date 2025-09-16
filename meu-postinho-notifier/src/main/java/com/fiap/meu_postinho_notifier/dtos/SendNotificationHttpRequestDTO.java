package com.fiap.meu_postinho_notifier.dtos;

public record SendNotificationHttpRequestDTO(
        String chatId,
        String reply_to,
        String text,
        boolean linkPreview,
        boolean linkPreviewHighQuality,
        String session
) {
}
