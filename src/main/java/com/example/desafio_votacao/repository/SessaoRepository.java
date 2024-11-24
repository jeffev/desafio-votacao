package com.example.desafio_votacao.repository;

import com.example.desafio_votacao.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * Lista todas as sessões com fim posterior à data e hora atual.
     *
     * @param dataHoraAtual a data e hora atual
     * @return uma lista de sessões abertas
     */
    List<Sessao> findByFimAfter(LocalDateTime dataHoraAtual);

    /**
     * Lista todas as sessões com fim posterior à data e hora atual, com paginação.
     *
     * @param dataHoraAtual a data e hora atual
     * @param pageable parâmetros de paginação
     * @return uma lista paginada de sessões abertas
     */
    List<Sessao> findByFimAfter(LocalDateTime dataHoraAtual, org.springframework.data.domain.Pageable pageable);
}
