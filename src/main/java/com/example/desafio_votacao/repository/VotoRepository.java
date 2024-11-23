package com.example.desafio_votacao.repository;

import com.example.desafio_votacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    boolean existsBySessaoIdAndAssociadoId(Long sessaoId, String associadoId);
}