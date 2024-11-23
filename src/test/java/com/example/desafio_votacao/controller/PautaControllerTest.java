package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.service.PautaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PautaControllerTest {

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private PautaController pautaController;

    private Pauta pauta;

    @BeforeEach
    public void setUp() {
        pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDescricao("Pauta de teste");
    }

    @Test
    public void testCriarPauta() {
        when(pautaService.criarPauta(pauta)).thenReturn(pauta);

        ResponseEntity<Pauta> response = pautaController.criarPauta(pauta);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(pauta, response.getBody());
        verify(pautaService, times(1)).criarPauta(pauta);
    }

    @Test
    public void testListarPautas() {
        List<Pauta> pautas = Arrays.asList(pauta);
        when(pautaService.listarPautas()).thenReturn(pautas);

        ResponseEntity<List<Pauta>> response = pautaController.listarPautas();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pautas, response.getBody());
        verify(pautaService, times(1)).listarPautas();
    }

    @Test
    public void testBuscarPauta() {
        when(pautaService.buscarPautaPorId(1L)).thenReturn(pauta);

        ResponseEntity<Pauta> response = pautaController.buscarPauta(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pauta, response.getBody());
        verify(pautaService, times(1)).buscarPautaPorId(1L);
    }
}
