package com.example.desafio_votacao.service;

import com.example.desafio_votacao.exception.ResourceNotFoundException;
import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public Pauta criarPauta(Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    public List<Pauta> listarPautas() {
        return pautaRepository.findAll();
    }

    public Pauta buscarPautaPorId(Long id) {
        return pautaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Pauta", id)
        );
    }
}
