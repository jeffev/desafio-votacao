package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.dto.VotoRequestDTO;
import com.example.desafio_votacao.model.Voto;
import com.example.desafio_votacao.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/votos")
public class VotoController {

    private static final Logger log = LoggerFactory.getLogger(VotoController.class);

    @Autowired
    private VotoService votoService;

    /**
     * Registra um voto para uma sessão.
     *
     * @param votoRequest o objeto contendo as informações do voto a ser registrado
     * @return ResponseEntity contendo o voto registrado e o código HTTP 200 (OK)
     */
    @PostMapping
    public ResponseEntity<Voto> registrarVoto(@RequestBody VotoRequestDTO votoRequest) {
        log.debug("Registrando voto para a sessão ID: {}, associado ID: {}", votoRequest.getSessaoId(), votoRequest.getAssociadoId());
        Voto voto = votoService.registrarVoto(votoRequest.getSessaoId(), votoRequest.getAssociadoId(), votoRequest.isVotoSim());
        log.info("Voto registrado com sucesso: {}", voto);
        return ResponseEntity.ok(voto);
    }

    /**
     * Obtém o resultado da votação de uma sessão específica.
     * Utiliza cache para melhorar a performance em sessões com muitos acessos ao resultado.
     *
     * @param sessaoId o ID da sessão de votação
     * @return ResponseEntity contendo o mapa de resultados, com a quantidade de votos "Sim" e "Não"
     */
    @GetMapping("/{sessaoId}/resultado")
    @Cacheable(value = "resultadoSessao", key = "#sessaoId")
    public ResponseEntity<Map<String, Long>> obterResultado(@PathVariable Long sessaoId) {
        log.debug("Obtendo resultado da sessão ID: {}", sessaoId);

        long votosSim = votoService.contarVotosSim(sessaoId);
        long votosNao = votoService.contarVotosNao(sessaoId);

        Map<String, Long> resultado = new HashMap<>();
        resultado.put("votosSim", votosSim);
        resultado.put("votosNao", votosNao);

        log.info("Resultado obtido: Votos Sim = {}, Votos Não = {}", votosSim, votosNao);

        return ResponseEntity.ok(resultado);
    }
}
