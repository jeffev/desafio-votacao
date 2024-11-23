package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.dto.VotoRequestDTO;
import com.example.desafio_votacao.model.Voto;
import com.example.desafio_votacao.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @PostMapping
    public ResponseEntity<Voto> registrarVoto(@RequestBody VotoRequestDTO votoRequest) {
        Voto voto = votoService.registrarVoto(votoRequest.getSessaoId(), votoRequest.getAssociadoId(), votoRequest.isVotoSim());
        return ResponseEntity.ok(voto);
    }

    @GetMapping("/{sessaoId}/resultado")
    public ResponseEntity<Map<String, Long>> obterResultado(@PathVariable Long sessaoId) {
        long votosSim = votoService.contarVotosSim(sessaoId);
        long votosNao = votoService.contarVotosNao(sessaoId);

        Map<String, Long> resultado = new HashMap<>();
        resultado.put("votosSim", votosSim);
        resultado.put("votosNao", votosNao);

        return ResponseEntity.ok(resultado);
    }

}
