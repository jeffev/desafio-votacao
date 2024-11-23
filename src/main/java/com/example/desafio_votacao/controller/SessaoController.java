package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @PostMapping("/abrir")
    public ResponseEntity<Sessao> abrirSessao(
            @RequestParam Long pautaId,
            @RequestParam(required = false) Integer duracaoEmMinutos) {
        return ResponseEntity.ok(sessaoService.abrirSessao(pautaId, duracaoEmMinutos));
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
}
