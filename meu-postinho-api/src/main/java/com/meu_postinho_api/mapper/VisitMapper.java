package com.meu_postinho_api.mapper;

import com.meu_postinho_api.dtos.requests.CreateVisitRequest;
import com.meu_postinho_api.dtos.responses.*;
import com.meu_postinho_api.entities.HealthAgent;
import com.meu_postinho_api.entities.Patient;
import com.meu_postinho_api.entities.Visit;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class VisitMapper {

    public CreateVisitResponseDTO toCreateVisitResponse(Visit visit) {
        if(visit == null) return null;

        HealthAgentResponseDTO healthAgentDTO = new HealthAgentResponseDTO(
                visit.getHealthAgent().getId(),
                visit.getHealthAgent().getName(),
                visit.getHealthAgent().getTelephone(),
                visit.getHealthAgent().getRegisterNumber(),
                visit.getHealthAgent().getHealthUnity()
        );

        PatientResponseDTO patientDTO = new PatientResponseDTO(
                visit.getPatient().getId(),
                visit.getPatient().getName(),
                visit.getPatient().getTelephone(),
                visit.getPatient().getEmail()
        );

        return new CreateVisitResponseDTO(
                visit.getId(),
                healthAgentDTO,
                patientDTO,
                visit.getVisitDate(),
                visit.getReason(),
                visit.getObservations(),
                visit.getStatus()
        );
    }

    public Visit toEntity(CreateVisitRequest request, HealthAgent healthAgent, Patient patient) {
        if(request == null) return null;

        return new Visit(
                healthAgent,
                patient,
                request.visitDate(),
                request.reason(),
                request.observations(),
                request.status()
        );
    }

    public VisitResponseDTO toDTO(Visit visit) {
        return new VisitResponseDTO(
                visit.getId(),
                visit.getVisitDate(),
                visit.getReason(),
                visit.getObservations(),
                visit.getStatus(),
                visit.getPatient().getName(),
                visit.getHealthAgent().getName()
        );
    }

    public FinishVisitResponseDTO toFinishVisitResponse(Visit visit) {
        if(visit == null) return null;

        HealthAgentResponseDTO healthAgentDTO = new HealthAgentResponseDTO(
                visit.getHealthAgent().getId(),
                visit.getHealthAgent().getName(),
                visit.getHealthAgent().getTelephone(),
                visit.getHealthAgent().getRegisterNumber(),
                visit.getHealthAgent().getHealthUnity()
        );

        PatientResponseDTO patientDTO = new PatientResponseDTO(
                visit.getPatient().getId(),
                visit.getPatient().getName(),
                visit.getPatient().getTelephone(),
                visit.getPatient().getEmail()
        );

        return new FinishVisitResponseDTO(
                visit.getId(),
                healthAgentDTO,
                patientDTO,
                visit.getVisitDate(),
                visit.getReason(),
                visit.getObservations(),
                visit.getStatus(),
                visit.getFinLatitude(),
                visit.getFinLongitude()
        );
    }
}
