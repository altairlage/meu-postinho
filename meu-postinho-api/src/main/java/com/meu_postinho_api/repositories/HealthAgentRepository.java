package com.meu_postinho_api.repositories;

import com.meu_postinho_api.entities.HealthAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthAgentRepository extends JpaRepository<HealthAgent, Long> {
}
