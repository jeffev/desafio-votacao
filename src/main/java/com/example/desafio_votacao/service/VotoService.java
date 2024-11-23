package com.example.desafio_votacao.service;

import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.model.Voto;
import com.example.desafio_votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoService sessaoService;

    public Voto registrarVoto(Long sessaoId, String associadoId, boolean votoSim) {
        Sessao sessao = sessaoService.buscarSessaoPorId(sessaoId);

        if (!sessaoService.isSessaoAberta(sessaoId)) {
            throw new RuntimeException("A sessão de votação já está encerrada.");
        }

        if (votoRepository.existsBySessaoIdAndAssociadoId(sessaoId, associadoId)) {
            throw new RuntimeException("O associado já votou nesta pauta.");
        }

        Voto voto = new Voto();
        voto.setSessao(sessao);
        voto.setAssociadoId(associadoId);
        voto.setVoto(votoSim);

        return votoRepository.save(voto);
    }

    public long contarVotosSim(Long sessaoId) {
        return votoRepository.findAll().stream()
                .filter(v -> v.getSessao().getId().equals(sessaoId) && v.isVoto())
                .count();
    }

    public long contarVotosNao(Long sessaoId) {
        return votoRepository.findAll().stream()
                .filter(v -> v.getSessao().getId().equals(sessaoId) && !v.isVoto())
                .count();
    }
}
