package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.dto.VotoRequestDTO;
import com.example.desafio_votacao.model.Voto;
import com.example.desafio_votacao.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/votos")
public class VotoController {

    private static final Logger log = LoggerFactory.getLogger(VotoController.class);

    @Autowired
    private VotoService votoService;

    @PostMapping
    public ResponseEntity<Voto> registrarVoto(@RequestBody VotoRequestDTO votoRequest) {
        log.info("Registrando voto para a sessão ID: {}, associado ID: {}", votoRequest.getSessaoId(), votoRequest.getAssociadoId());
        Voto voto = votoService.registrarVoto(votoRequest.getSessaoId(), votoRequest.getAssociadoId(), votoRequest.isVotoSim());
        log.info("Voto registrado com sucesso: {}", voto);
        return ResponseEntity.ok(voto);
    }

    @GetMapping("/{sessaoId}/resultado")
    public ResponseEntity<Map<String, Long>> obterResultado(@PathVariable Long sessaoId) {
        log.info("Obtendo resultado da sessão ID: {}", sessaoId);
        long votosSim = votoService.contarVotosSim(sessaoId);
        long votosNao = votoService.contarVotosNao(sessaoId);

        Map<String, Long> resultado = new HashMap<>();
        resultado.put("votosSim", votosSim);
        resultado.put("votosNao", votosNao);

        log.info("Resultado obtido: Votos Sim = {}, Votos Não = {}", votosSim, votosNao);

        return ResponseEntity.ok(resultado);
    }
}