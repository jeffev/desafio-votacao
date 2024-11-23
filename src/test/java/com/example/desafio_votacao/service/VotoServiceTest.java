package com.example.desafio_votacao.service;

import com.example.desafio_votacao.exception.ResourceNotFoundException;
import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotoServiceTest {

    @InjectMocks
    private VotoService votoService;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private SessaoService sessaoService;

    private Long sessaoId;
    private String associadoId;
    private boolean votoSim;

    @BeforeEach
    void setUp() {
        sessaoId = 1L;
        associadoId = "12345";
        votoSim = true;
    }

    @Test
    void testRegistrarVotoEmSessaoFechada() {
        Sessao sessao = new Sessao();
        sessao.setId(sessaoId);
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        sessao.setPauta(pauta);

        when(sessaoService.buscarSessaoPorId(sessaoId)).thenReturn(sessao);
        when(sessaoService.isSessaoAberta(sessaoId)).thenReturn(false);

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            votoService.registrarVoto(sessaoId, associadoId, votoSim);
        });

        assertEquals("A sessão de votação está encerrada.", thrown.getMessage());
    }

    @Test
    void testRegistrarVotoComAssociadoJaVotado() {
        Sessao sessao = new Sessao();
        sessao.setId(sessaoId);
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        sessao.setPauta(pauta);

        when(sessaoService.buscarSessaoPorId(sessaoId)).thenReturn(sessao);
        when(sessaoService.isSessaoAberta(sessaoId)).thenReturn(true);
        when(votoRepository.existsBySessao_Pauta_IdAndAssociadoId(pauta.getId(), associadoId)).thenReturn(true);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            votoService.registrarVoto(sessaoId, associadoId, votoSim);
        });

        assertEquals("O associado já votou nesta pauta.", thrown.getMessage());
    }

    @Test
    void testContarVotosSim() {
        long votosSim = 10L;

        when(votoRepository.countBySessaoIdAndVoto(sessaoId, true)).thenReturn(votosSim);

        long resultado = votoService.contarVotosSim(sessaoId);

        assertEquals(votosSim, resultado);
    }

    @Test
    void testContarVotosNao() {
        long votosNao = 5L;

        when(votoRepository.countBySessaoIdAndVoto(sessaoId, false)).thenReturn(votosNao);

        long resultado = votoService.contarVotosNao(sessaoId);

        assertEquals(votosNao, resultado);
    }

    @Test
    void testContarVotosComSessaoNaoExistente() {
        when(sessaoService.buscarSessaoPorId(sessaoId)).thenThrow(new ResourceNotFoundException("Sessão", sessaoId));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            votoService.contarVotosSim(sessaoId);
        });

        assertEquals("Sessão não encontrado(a) com o ID: 1", thrown.getMessage());
    }
}
