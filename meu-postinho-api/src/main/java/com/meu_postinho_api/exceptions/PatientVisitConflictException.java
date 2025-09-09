package com.meu_postinho_api.exceptions;

public class PatientVisitConflictException extends RuntimeException {
    public PatientVisitConflictException(String message) {
        super(message);
    }
}
