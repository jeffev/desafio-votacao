package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.service.SessaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SessaoControllerTest {

    @Mock
    private SessaoService sessaoService;

    @InjectMocks
    private SessaoController sessaoController;

    private Sessao sessao;

    @BeforeEach
    public void setUp() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setInicio(LocalDateTime.now());
        sessao.setFim(LocalDateTime.now().plusMinutes(30));
    }

    @Test
    public void testAbrirSessao() {
        when(sessaoService.abrirSessao(1L, 30)).thenReturn(sessao);

        ResponseEntity<Sessao> response = sessaoController.abrirSessao(1L, 30);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sessao, response.getBody());
        verify(sessaoService, times(1)).abrirSessao(1L, 30);
    }

    @Test
    public void testBuscarSessao() {
        when(sessaoService.buscarSessaoPorId(1L)).thenReturn(sessao);

        ResponseEntity<Sessao> response = sessaoController.buscarSessao(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sessao, response.getBody());
        verify(sessaoService, times(1)).buscarSessaoPorId(1L);
    }

    @Test
    public void testVerificarStatusSessaoAberta() {
        when(sessaoService.isSessaoAberta(1L)).thenReturn(true);

        ResponseEntity<String> response = sessaoController.verificarStatusSessao(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sessão aberta", response.getBody());
        verify(sessaoService, times(1)).isSessaoAberta(1L);
    }

    @Test
    public void testVerificarStatusSessaoEncerrada() {
        when(sessaoService.isSessaoAberta(1L)).thenReturn(false);

        ResponseEntity<String> response = sessaoController.verificarStatusSessao(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sessão encerrada", response.getBody());
        verify(sessaoService, times(1)).isSessaoAberta(1L);
    }
}