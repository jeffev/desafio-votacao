package com.example.desafio_votacao.service;

import com.example.desafio_votacao.exception.ResourceNotFoundException;
import com.example.desafio_votacao.exception.ValidationException;
import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    /**
     * Cria uma nova pauta com validações.
     * @param pauta a pauta a ser criada.
     * @return a pauta salva.
     */
    public Pauta criarPauta(Pauta pauta) {
        validarPauta(pauta);
        return pautaRepository.save(pauta);
    }

    /**
     * Lista todas as pautas com paginação.
     * @param page número da página.
     * @param size tamanho da página.
     * @return lista de pautas.
     */
    @Cacheable("pautas")
    public List<Pauta> listarPautas(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Pauta> pautasPage = pautaRepository.findAll(pageable);
        return pautasPage.getContent();
    }

    /**
     * Busca uma pauta pelo ID com validação de existência.
     * @param id o ID da pauta a ser buscada.
     * @return a pauta encontrada.
     */
    public Pauta buscarPautaPorId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("O ID da pauta deve ser maior que zero.");
        }

        return pautaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Pauta", id)
        );
    }

    /**
     * Valida os dados de uma pauta.
     * @param pauta a pauta a ser validada.
     */
    private void validarPauta(Pauta pauta) {
        if (pauta == null) {
            throw new ValidationException("A pauta não pode ser nula.");
        }

        if (pauta.getTitulo() == null || pauta.getTitulo().trim().isEmpty()) {
            throw new ValidationException("O título da pauta é obrigatório.");
        }

        if (pauta.getTitulo().length() > 255) {
            throw new ValidationException("O título da pauta não pode ter mais de 255 caracteres.");
        }

        if (pauta.getDescricao() == null || pauta.getDescricao().trim().isEmpty()) {
            throw new ValidationException("A descrição da pauta é obrigatória.");
        }

        if (pauta.getDescricao().length() > 1000) {
            throw new ValidationException("A descrição da pauta não pode ter mais de 1000 caracteres.");
        }
    }
}
