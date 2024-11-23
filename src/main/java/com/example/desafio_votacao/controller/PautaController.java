package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public ResponseEntity<Pauta> criarPauta(@RequestBody Pauta pauta) {
        return ResponseEntity.ok(pautaService.criarPauta(pauta));
    }

    @GetMapping
    public ResponseEntity<List<Pauta>> listarPautas() {
        return ResponseEntity.ok(pautaService.listarPautas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscarPauta(@PathVariable Long id) {
        return ResponseEntity.ok(pautaService.buscarPautaPorId(id));
    }
}
