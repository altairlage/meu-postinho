package com.meu_postinho_api.dtos.requests;

import java.math.BigDecimal;

public record FinishVisitRequestDTO(
        BigDecimal latitude,
        BigDecimal longitude
) {
}
