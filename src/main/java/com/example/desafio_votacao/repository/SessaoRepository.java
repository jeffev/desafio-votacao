package com.example.desafio_votacao.repository;

import com.example.desafio_votacao.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    /**
     * Verifica se existe uma sessão aberta (fim posterior ao momento atual) para a pauta especificada.
     *
     * @param pautaId o ID da pauta
     * @param dataHoraAtual a data e hora atual
     * @return true se houver uma sessão aberta, caso contrário false
     */
    boolean existsByPautaIdAndFimAfter(Long pautaId, LocalDateTime dataHoraAtual);
}
