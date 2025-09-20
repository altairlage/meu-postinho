package com.meu_postinho_api.dtos.requests;

import com.meu_postinho_api.enums.VisitStatusEnum;

public record FinishVisitRequestDTO(
        VisitStatusEnum status,

) {
}
