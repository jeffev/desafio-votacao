package com.example.desafio_votacao.service;

import com.example.desafio_votacao.exception.ResourceNotFoundException;
import com.example.desafio_votacao.exception.ValidationException;
import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.repository.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    private Pauta pauta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Título da Pauta");
        pauta.setDescricao("Descrição da Pauta");
    }


    @Test
    void testCriarPautaComTituloValido() {
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

        Pauta result = pautaService.criarPauta(pauta);

        assertNotNull(result);
        assertEquals("Título da Pauta", result.getTitulo());
        verify(pautaRepository, times(1)).save(pauta);
    }

    @Test
    void testCriarPautaComTituloInvalido() {
        pauta.setTitulo("");
        pauta.setDescricao("Descrição válida.");

        ValidationException thrown = assertThrows(ValidationException.class, () -> pautaService.criarPauta(pauta));
        assertEquals("O título da pauta é obrigatório.", thrown.getMessage());
    }

    @Test
    void testListarPautas() {
        Pauta pauta = new Pauta();
        Pageable pageable = PageRequest.of(1, 10);

        when(pautaRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(pauta)));

        var result = pautaService.listarPautas(1, 10);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(pautaRepository, times(1)).findAll(pageable);
    }

    @Test
    void testBuscarPautaPorIdExistente() {
        pauta.setId(1L);
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        Pauta result = pautaService.buscarPautaPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(pautaRepository, times(1)).findById(1L);
    }


    @Test
    void testBuscarPautaPorIdNaoExistente() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> pautaService.buscarPautaPorId(1L));
        assertEquals("Pauta não encontrado(a) com o ID: 1", thrown.getMessage());
    }


    @Test
    void testBuscarPautaPorIdInvalido() {
        ValidationException thrown = assertThrows(ValidationException.class, () -> pautaService.buscarPautaPorId(-1L));
        assertEquals("O ID da pauta deve ser maior que zero.", thrown.getMessage());
    }
}
