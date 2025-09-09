package com.meu_postinho_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SendUpdateVisitNotification(
        @JsonProperty("id") Long id
) {
}
