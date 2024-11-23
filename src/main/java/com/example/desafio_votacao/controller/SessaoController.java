package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.dto.SessaoRequestDTO;
import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.service.SessaoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    private static final Logger log = LoggerFactory.getLogger(VotoController.class);

    @PostMapping("/abrir")
    public ResponseEntity<Sessao> abrirSessao(@RequestBody SessaoRequestDTO sessaoRequestDTO) {
        log.info("Requisição para abrir sessão: pautaId = {}, duração = {} minutos",
                sessaoRequestDTO.getPautaId(), sessaoRequestDTO.getDuracaoEmMinutos());
        Sessao sessao = sessaoService.abrirSessao(sessaoRequestDTO.getPautaId(), sessaoRequestDTO.getDuracaoEmMinutos());
        log.info("Sessão aberta com sucesso: {}", sessao);
        return ResponseEntity.ok(sessao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sessao> buscarSessao(@PathVariable Long id) {
        log.info("Requisição para buscar sessão com ID: {}", id);
        Sessao sessao = sessaoService.buscarSessaoPorId(id);
        if (sessao != null) {
            log.info("Sessão encontrada: {}", sessao);
        } else {
            log.warn("Sessão com ID {} não encontrada", id);
        }
        return ResponseEntity.ok(sessao);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> verificarStatusSessao(@PathVariable Long id) {
        log.info("Requisição para verificar status da sessão com ID: {}", id);
        boolean aberta = sessaoService.isSessaoAberta(id);
        String status = aberta ? "Sessão aberta" : "Sessão encerrada";
        log.info("Status da sessão ID {}: {}", id, status);
        return ResponseEntity.ok(status);
    }

    @GetMapping
    public ResponseEntity<List<Sessao>> listarSessoes() {
        log.info("Requisição para listar todas as sessões");
        List<Sessao> sessoes = sessaoService.listarTodasSessoes();
        log.info("Total de sessões encontradas: {}", sessoes.size());
        return ResponseEntity.ok(sessoes);
    }

    @GetMapping("/abertas")
    public ResponseEntity<List<Sessao>> listarSessoesAbertas() {
        log.info("Requisição para listar sessões abertas");
        List<Sessao> sessoesAbertas = sessaoService.listarSessoesAbertas();
        log.info("Total de sessões abertas encontradas: {}", sessoesAbertas.size());
        return ResponseEntity.ok(sessoesAbertas);
    }
}