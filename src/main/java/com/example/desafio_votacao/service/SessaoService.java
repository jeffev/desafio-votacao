package com.example.desafio_votacao.service;

import com.example.desafio_votacao.exception.ResourceNotFoundException;
import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private PautaService pautaService;

    public Sessao abrirSessao(Long pautaId, Integer duracaoEmMinutos) {
        Pauta pauta = pautaService.buscarPautaPorId(pautaId);

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setInicio(LocalDateTime.now());
        sessao.setFim(LocalDateTime.now().plusMinutes(duracaoEmMinutos != null ? duracaoEmMinutos : 1));

        return sessaoRepository.save(sessao);
    }

    public Sessao buscarSessaoPorId(Long id) {
        return sessaoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Sessão", id)
        );
    }

    public boolean isSessaoAberta(Long sessaoId) {
        Sessao sessao = buscarSessaoPorId(sessaoId);
        return LocalDateTime.now().isBefore(sessao.getFim());
    }
}
