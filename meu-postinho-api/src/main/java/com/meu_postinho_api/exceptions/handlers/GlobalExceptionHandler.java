package com.meu_postinho_api.exceptions.handlers;

import com.meu_postinho_api.dtos.responses.ExceptionResponseDTO;
import com.meu_postinho_api.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PatientVisitConflictException.class)
    public ResponseEntity<ExceptionResponseDTO> handleVisitScheduleConflictException(PatientVisitConflictException ex) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(HealthAgentVisitConflictException.class)
    public ResponseEntity<ExceptionResponseDTO> handleHealthAgentVisitConflictException(HealthAgentVisitConflictException ex) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(VisitStatusNotUpdatedException.class)
    public ResponseEntity<ExceptionResponseDTO> handleVisitStatusNotUpdatedException(VisitStatusNotUpdatedException ex) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleVisitNotFoundException(VisitNotFoundException ex) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(HealtAgentNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleHealtAgentNotFoundException(HealtAgentNotFoundException ex) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handlePatientNotFoundException(PatientNotFoundException ex) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
