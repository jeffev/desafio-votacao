package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.service.PautaService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    private static final Logger log = LoggerFactory.getLogger(VotoController.class);

    @PostMapping
    public ResponseEntity<Pauta> criarPauta(@RequestBody Pauta pauta) {
        log.info("Requisição para criar pauta: {}", pauta);
        Pauta pautaCriada = pautaService.criarPauta(pauta);
        log.info("Pauta criada com sucesso: {}", pautaCriada);
        return ResponseEntity.created(URI.create("/pautas/" + pautaCriada.getId()))
                .body(pautaCriada);
    }

    @GetMapping
    public ResponseEntity<List<Pauta>> listarPautas() {
        log.info("Requisição para listar todas as pautas");
        List<Pauta> pautas = pautaService.listarPautas();
        log.info("Total de pautas encontradas: {}", pautas.size());
        return ResponseEntity.ok(pautas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscarPauta(@PathVariable Long id) {
        log.info("Requisição para buscar pauta com ID: {}", id);
        Pauta pauta = pautaService.buscarPautaPorId(id);
        if (pauta != null) {
            log.info("Pauta encontrada: {}", pauta);
        } else {
            log.warn("Pauta com ID {} não encontrada", id);
        }
        return ResponseEntity.ok(pauta);
    }
}
