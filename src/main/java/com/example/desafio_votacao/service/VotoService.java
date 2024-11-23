package com.example.desafio_votacao.service;

import com.example.desafio_votacao.exception.ResourceNotFoundException;
import com.example.desafio_votacao.facade.CpfValidationFacade;
import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.model.Voto;
import com.example.desafio_votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private CpfValidationFacade cpfValidationFacade;

    /**
     * Registra um voto para uma sessão, validando se a sessão está aberta,
     * se o associado já votou e se o CPF do associado é válido para votar.
     *
     * @param sessaoId    o ID da sessão
     * @param associadoId o identificador único do associado
     * @param votoSim     true para voto "Sim", false para voto "Não"
     * @return o voto registrado
     */
    @Transactional
    public Voto registrarVoto(Long sessaoId, String associadoId, boolean votoSim) {
        Sessao sessao = sessaoService.buscarSessaoPorId(sessaoId);

        if (!sessaoService.isSessaoAberta(sessaoId)) {
            throw new IllegalStateException("A sessão de votação está encerrada.");
        }

        if (votoRepository.existsBySessao_Pauta_IdAndAssociadoId(sessao.getPauta().getId(), associadoId)) {
            throw new IllegalArgumentException("O associado já votou nesta pauta.");
        }

        String statusCpf = cpfValidationFacade.validarCpf(associadoId);
        if ("UNABLE_TO_VOTE".equals(statusCpf)) {
            throw new IllegalArgumentException("O CPF não é elegível para votar.");
        }

        Voto voto = new Voto();
        voto.setSessao(sessao);
        voto.setAssociadoId(associadoId);
        voto.setVoto(votoSim);

        return votoRepository.save(voto);
    }

    /**
     * Conta o número de votos "Sim" em uma sessão específica.
     *
     * @param sessaoId o ID da sessão
     * @return o total de votos "Sim"
     */
    public long contarVotosSim(Long sessaoId) {
        validarSessaoExistente(sessaoId);
        return votoRepository.countBySessaoIdAndVoto(sessaoId, true);
    }

    /**
     * Conta o número de votos "Não" em uma sessão específica.
     *
     * @param sessaoId o ID da sessão
     * @return o total de votos "Não"
     */
    public long contarVotosNao(Long sessaoId) {
        validarSessaoExistente(sessaoId);
        return votoRepository.countBySessaoIdAndVoto(sessaoId, false);
    }

    /**
     * Valida se a sessão existe antes de realizar qualquer operação.
     *
     * @param sessaoId o ID da sessão
     */
    private void validarSessaoExistente(Long sessaoId) {
        try {
            sessaoService.buscarSessaoPorId(sessaoId);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Sessão", sessaoId);
        }
    }
}