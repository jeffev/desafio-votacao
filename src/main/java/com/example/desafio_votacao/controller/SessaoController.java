package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.dto.SessaoRequestDTO;
import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @PostMapping("/abrir")
    public ResponseEntity<Sessao> abrirSessao(@RequestBody SessaoRequestDTO sessaoRequestDTO) {
        return ResponseEntity.ok(sessaoService.abrirSessao(sessaoRequestDTO.getPautaId(), sessaoRequestDTO.getDuracaoEmMinutos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sessao> buscarSessao(@PathVariable Long id) {
        return ResponseEntity.ok(sessaoService.buscarSessaoPorId(id));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> verificarStatusSessao(@PathVariable Long id) {
        boolean aberta = sessaoService.isSessaoAberta(id);
        return ResponseEntity.ok(aberta ? "Sessão aberta" : "Sessão encerrada");
    }

    // Endpoint para listar todas as sessões
    @GetMapping
    public ResponseEntity<List<Sessao>> listarSessoes() {
        List<Sessao> sessoes = sessaoService.listarTodasSessoes();
        return ResponseEntity.ok(sessoes);
    }

    // Endpoint para listar as sessões abertas
    @GetMapping("/abertas")
    public ResponseEntity<List<Sessao>> listarSessoesAbertas() {
        List<Sessao> sessoesAbertas = sessaoService.listarSessoesAbertas();
        return ResponseEntity.ok(sessoesAbertas);
    }
}