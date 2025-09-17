package com.meu_postinho_api.repositories;

import com.meu_postinho_api.entities.Visit;
import com.meu_postinho_api.enums.VisitStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findAllByStatus(VisitStatusEnum status);

    List<Visit> findAllByPatient_Id(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE visitas SET status = :status WHERE id = :id;", nativeQuery = true)
    int updateVisitStatus(@Param("status") String status, @Param("id") Long id);

    boolean existsByPatient_IdAndVisitDate(Long patientId, LocalDateTime visitDate);

    boolean existsByHealthAgent_IdAndVisitDate(Long healthAgentId, LocalDateTime visitDate);
}
