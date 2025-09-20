package com.meu_postinho_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meu_postinho_api.enums.VisitStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "visitas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Visit implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_agente_saude")
    @JsonIgnore
    private HealthAgent healthAgent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_paciente")
    @JsonIgnore
    private Patient patient;

    @Column(name = "data_visita", nullable = false)
    private LocalDateTime visitDate;

    @Column(name = "motivo")
    private String reason;

    @Column(name = "observacoes")
    private String observations;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VisitStatusEnum status;

    @Column(name = "fin_latitude")
    private String finLatitude;

    @Column(name = "fin_longitude")
    private String finLongitude;

    public Visit(HealthAgent healthAgent, Patient patient, LocalDateTime visitDate, String reason, String observations, VisitStatusEnum status) {
        this.healthAgent = healthAgent;
        this.patient = patient;
        this.visitDate = visitDate;
        this.reason = reason;
        this.observations = observations;
        this.status = status;
    }
}
