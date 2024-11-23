package com.example.desafio_votacao.service;

import com.example.desafio_votacao.exception.ResourceNotFoundException;
import com.example.desafio_votacao.exception.ValidationException;
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
        if (pautaId == null) {
            throw new ValidationException("O ID da pauta é obrigatório.");
        }

        if (duracaoEmMinutos != null && duracaoEmMinutos <= 0) {
            throw new ValidationException("A duração da sessão deve ser maior que zero.");
        }

        Pauta pauta = pautaService.buscarPautaPorId(pautaId);

        if (sessaoRepository.existsByPautaIdAndFimAfter(pautaId, LocalDateTime.now())) {
            throw new ValidationException("Já existe uma sessão aberta para a pauta informada.");
        }

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setInicio(LocalDateTime.now());
        sessao.setFim(LocalDateTime.now().plusMinutes(duracaoEmMinutos != null ? duracaoEmMinutos : 1));

        return sessaoRepository.save(sessao);
    }

    public Sessao buscarSessaoPorId(Long id) {
        if (id == null) {
            throw new ValidationException("O ID da sessão é obrigatório.");
        }

        return sessaoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Sessão", id)
        );
    }

    public boolean isSessaoAberta(Long sessaoId) {
        Sessao sessao = buscarSessaoPorId(sessaoId);
        boolean isAberta = LocalDateTime.now().isBefore(sessao.getFim());
        if (!isAberta) {
            throw new ValidationException("A sessão está fechada.");
        }
        return isAberta;
    }
}
