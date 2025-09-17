package com.meu_postinho_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "agentes_saude")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HealthAgent implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "telefone", nullable = false)
    private String telephone;

    @Column(name = "numero_registro", nullable = false)
    private String registerNumber;

    @Column(name = "unidade_saude", nullable = false)
    private String healthUnity;

    @OneToMany(mappedBy = "healthAgent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visit> visitas;
}
