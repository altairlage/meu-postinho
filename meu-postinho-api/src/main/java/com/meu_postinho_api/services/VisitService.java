package com.meu_postinho_api.services;

import com.meu_postinho_api.dtos.SendVisitNotification;
import com.meu_postinho_api.dtos.requests.CreateVisitRequest;
import com.meu_postinho_api.dtos.requests.FinishVisitRequestDTO;
import com.meu_postinho_api.dtos.requests.UpdateStatusRequestDTO;
import com.meu_postinho_api.dtos.responses.CreateVisitResponseDTO;
import com.meu_postinho_api.dtos.responses.FinishVisitResponseDTO;
import com.meu_postinho_api.dtos.responses.VisitResponseDTO;
import com.meu_postinho_api.entities.HealthAgent;
import com.meu_postinho_api.entities.Patient;
import com.meu_postinho_api.entities.Visit;
import com.meu_postinho_api.enums.VisitStatusEnum;
import com.meu_postinho_api.exceptions.*;
import com.meu_postinho_api.mapper.NotificationMapper;
import com.meu_postinho_api.mapper.VisitMapper;
import com.meu_postinho_api.producer.KafkaProducerApplication;
import com.meu_postinho_api.repositories.HealthAgentRepository;
import com.meu_postinho_api.repositories.PatientRepository;
import com.meu_postinho_api.repositories.VisitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final HealthAgentRepository healthAgentRepository;
    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final NotificationMapper notificationMapper;
    private final KafkaProducerApplication kafkaProducer;

    @Transactional
    public CreateVisitResponseDTO createVisit(CreateVisitRequest request) {
        HealthAgent healthAgent = healthAgentRepository.findById(request.healthAgentId())
                .orElseThrow(() -> new HealtAgentNotFoundException(String.format("Health Agent not found with id: %s", request.healthAgentId())));

        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new PatientNotFoundException(String.format("Patient not found with id: %s", request.patientId())));

        boolean existsVisitForPatient = visitRepository.existsByPatient_IdAndVisitDate(request.patientId(), request.visitDate());

        if (existsVisitForPatient) throw new PatientVisitConflictException("There's a visit already scheduled for this patient.");

        boolean existsVisitForHealthAgent = visitRepository.existsByHealthAgent_IdAndVisitDate(request.healthAgentId(), request.visitDate());

        if (existsVisitForHealthAgent) throw new HealthAgentVisitConflictException("There's a visit already scheduled for this health agent.");

        Visit visit = visitMapper.toEntity(request, healthAgent, patient);

        Visit savedVisit = visitRepository.save(visit);

        SendVisitNotification notification = notificationMapper.toVisitNotification(savedVisit);

        kafkaProducer.sendVisitMessage(notification);

        return visitMapper.toCreateVisitResponse(savedVisit);
    }

    public int updateVisitStatus(UpdateStatusRequestDTO request, Long id) {
        Visit updatedVisit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitStatusNotUpdatedException(String.format("Visit not found with id: %d", id)));

        boolean existsVisitForPatient = visitRepository.existsByPatient_IdAndVisitDate(updatedVisit.getPatient().getId(), updatedVisit.getVisitDate());

        if (existsVisitForPatient) throw new PatientVisitConflictException("There's a visit already scheduled for this patient.");

        boolean existsVisitForHealthAgent = visitRepository.existsByHealthAgent_IdAndVisitDate(updatedVisit.getHealthAgent().getId(), updatedVisit.getVisitDate());

        if (existsVisitForHealthAgent) throw new HealthAgentVisitConflictException("There's a visit already scheduled for this health agent.");

        int update = visitRepository.updateVisitStatus(request.status().name(), id);

        if (update == 0) throw new VisitStatusNotUpdatedException("Visit status could not have been updated.");

        SendVisitNotification notification = notificationMapper.toVisitNotification(updatedVisit);

        kafkaProducer.sendVisitMessage(notification);

        return update;
    }

    public FinishVisitResponseDTO finishVisit(FinishVisitRequestDTO request, Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitStatusNotUpdatedException(String.format("Visit not found with id: %d", id)));

        if(visit.getStatus().equals(VisitStatusEnum.CONCLUIDA)){
            throw new VisitStatusNotUpdatedException("Visit already finished.");
        }

        visit.setFinLatitude(request.latitude());
        visit.setFinLongitude(request.longitude());
        visit.setStatus(VisitStatusEnum.CONCLUIDA);

        Visit updatedVisit = visitRepository.save(visit);

        SendVisitNotification notification = notificationMapper.toVisitNotification(updatedVisit);

        kafkaProducer.sendVisitMessage(notification);

        return visitMapper.toFinishVisitResponse(updatedVisit);
    }

    public List<VisitResponseDTO> getVisitsByStatus(VisitStatusEnum status) {
        return visitRepository.findAllByStatus(status)
                .stream()
                .map(visitMapper::toDTO)
                .toList();
    }

    public List<VisitResponseDTO> getVisitsByPatientId(Long id) {
        return visitRepository.findAllByPatient_Id(id)
                .stream()
                .map(visitMapper::toDTO)
                .toList();
    }
}
