package com.example.desafio_votacao.repository;

import com.example.desafio_votacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    /**
     * Verifica se um associado já votou em uma pauta específica.
     *
     * @param pautaId   o ID da sessão
     * @param associadoId o identificador único do associado
     * @return true se o associado já votou, caso contrário false
     */
    boolean existsBySessao_Pauta_IdAndAssociadoId(Long pautaId, String associadoId);

    /**
     * Conta o número de votos "Sim" em uma sessão específica.
     *
     * @param sessaoId o ID da sessão
     * @return o número de votos "Sim"
     */
    long countBySessaoIdAndVoto(Long sessaoId, boolean voto);

    /**
     * Conta o número total de votos em uma sessão específica.
     *
     * @param sessaoId o ID da sessão
     * @return o número total de votos
     */
    long countBySessaoId(Long sessaoId);
}
