package com.meu_postinho_api.controllers;

import com.meu_postinho_api.dtos.requests.CreateVisitRequest;
import com.meu_postinho_api.dtos.requests.FinishVisitRequestDTO;
import com.meu_postinho_api.dtos.requests.UpdateStatusRequestDTO;
import com.meu_postinho_api.dtos.responses.CreateVisitResponseDTO;
import com.meu_postinho_api.dtos.responses.FinishVisitResponseDTO;
import com.meu_postinho_api.dtos.responses.VisitResponseDTO;
import com.meu_postinho_api.enums.VisitStatusEnum;
import com.meu_postinho_api.services.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VisitController {
    private final VisitService visitService;

    @PostMapping("/create")
    public ResponseEntity<CreateVisitResponseDTO> createVisit(@RequestBody CreateVisitRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(visitService.createVisit(request));
    }

    @PutMapping("/finish/{id}")
    public ResponseEntity<FinishVisitResponseDTO> finishVisit(@RequestBody FinishVisitRequestDTO request, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(visitService.finishVisit(request, id));
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<Void> updateVisitStatus(@RequestBody UpdateStatusRequestDTO request, @PathVariable Long id) {
        visitService.updateVisitStatus(request, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list-by-status/{status}")
    public ResponseEntity<List<VisitResponseDTO>> getVisitsByStatus(@PathVariable VisitStatusEnum status) {
        return ResponseEntity.ok(visitService.getVisitsByStatus(status));
    }

    @GetMapping("/list-by-patient/{id}")
    public ResponseEntity<List<VisitResponseDTO>> getVisitsByPatientId(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.getVisitsByPatientId(id));
    }
}
