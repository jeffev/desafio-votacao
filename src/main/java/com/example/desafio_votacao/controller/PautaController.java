package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.service.PautaService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controlador para gerenciar as pautas.
 * Fornece endpoints para criar, listar e buscar pautas.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    private static final Logger log = LoggerFactory.getLogger(PautaController.class);

    /**
     * Cria uma nova pauta.
     *
     * @param pauta o objeto Pauta a ser criado
     * @return ResponseEntity contendo o objeto Pauta criado e o código HTTP 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Pauta> criarPauta(@RequestBody Pauta pauta) {
        log.info("Requisição para criar pauta: {}", pauta);
        Pauta pautaCriada = pautaService.criarPauta(pauta);
        log.info("Pauta criada com sucesso: {}", pautaCriada);
        return ResponseEntity.created(URI.create("/api/v1/pautas/" + pautaCriada.getId()))
                .body(pautaCriada);
    }

    /**
     * Lista todas as pautas com paginação.
     *
     * @param page o número da página a ser retornada (padrão: 0)
     * @param size o tamanho da página a ser retornada (padrão: 10)
     * @return ResponseEntity contendo a lista de pautas e o código HTTP 200 (OK)
     */
    @GetMapping
    @Cacheable("pautas")
    public ResponseEntity<List<Pauta>> listarPautas(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        log.info("Requisição para listar todas as pautas, página: {}, tamanho: {}", page, size);
        List<Pauta> pautas = pautaService.listarPautas(page, size);
        log.info("Total de pautas encontradas: {}", pautas.size());
        return ResponseEntity.ok(pautas);
    }

    /**
     * Busca uma pauta pelo ID.
     *
     * @param id o ID da pauta a ser buscada
     * @return ResponseEntity contendo a pauta encontrada e o código HTTP 200 (OK)
     *         ou código 404 (Not Found) se a pauta não for encontrada
     */
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
