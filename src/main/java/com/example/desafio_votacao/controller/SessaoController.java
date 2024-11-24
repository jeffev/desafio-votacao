package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.dto.SessaoRequestDTO;
import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.service.SessaoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    /**
     * Endpoint para abrir uma nova sessão.
     */
    @PostMapping("/abrir")
    public ResponseEntity<Sessao> abrirSessao(@RequestBody SessaoRequestDTO sessaoRequestDTO) {
        log.info("Requisição para abrir sessão: pautaId = {}, duração = {} minutos",
                sessaoRequestDTO.getPautaId(), sessaoRequestDTO.getDuracaoEmMinutos());
        Sessao sessao = sessaoService.abrirSessao(sessaoRequestDTO.getPautaId(), sessaoRequestDTO.getDuracaoEmMinutos());
        log.info("Sessão aberta com sucesso: {}", sessao);
        return ResponseEntity.ok(sessao);
    }

    /**
     * Endpoint para buscar uma sessão específica.
     */
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

    /**
     * Endpoint para verificar o status de uma sessão.
     */
    @GetMapping("/{id}/status")
    public ResponseEntity<String> verificarStatusSessao(@PathVariable Long id) {
        log.info("Requisição para verificar status da sessão com ID: {}", id);
        boolean aberta = sessaoService.isSessaoAberta(id);
        String status = aberta ? "Sessão aberta" : "Sessão encerrada";
        log.info("Status da sessão ID {}: {}", id, status);
        return ResponseEntity.ok(status);
    }

    /**
     * Endpoint para listar todas as sessões com paginação.
     */
    @GetMapping
    public ResponseEntity<List<Sessao>> listarSessoes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Requisição para listar todas as sessões (Página: {}, Tamanho: {})", page, size);
        Pageable pageable = PageRequest.of(page, size);
        List<Sessao> sessoes = sessaoService.listarTodasSessoes(pageable);
        log.info("Total de sessões encontradas: {}", sessoes.size());
        return ResponseEntity.ok(sessoes);
    }

    /**
     * Endpoint para listar sessões abertas com paginação.
     */
    @GetMapping("/abertas")
    public ResponseEntity<List<Sessao>> listarSessoesAbertas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Requisição para listar sessões abertas (Página: {}, Tamanho: {})", page, size);
        Pageable pageable = PageRequest.of(page, size);
        List<Sessao> sessoesAbertas = sessaoService.listarSessoesAbertas(pageable);
        log.info("Total de sessões abertas encontradas: {}", sessoesAbertas.size());
        return ResponseEntity.ok(sessoesAbertas);
    }
}