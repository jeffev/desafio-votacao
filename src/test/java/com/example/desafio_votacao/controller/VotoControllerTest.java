package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.model.Voto;
import com.example.desafio_votacao.service.VotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class VotoControllerTest {

    @InjectMocks
    private VotoController votoController;

    @Mock
    private VotoService votoService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(votoController).build();
    }

    @Test
    public void testRegistrarVoto() throws Exception {
        Long sessaoId = 1L;
        String associadoId = "associado123";
        boolean votoSim = true;

        Sessao sessao = new Sessao();
        sessao.setId(sessaoId);

        Voto voto = new Voto();
        voto.setSessao(sessao);
        voto.setAssociadoId(associadoId);
        voto.setVoto(votoSim);

        when(votoService.registrarVoto(sessaoId, associadoId, votoSim)).thenReturn(voto);

        mockMvc.perform(post("/api/votos")
                        .param("sessaoId", String.valueOf(sessaoId))
                        .param("associadoId", associadoId)
                        .param("votoSim", String.valueOf(votoSim)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessao.id").value(sessaoId))
                .andExpect(jsonPath("$.associadoId").value(associadoId))
                .andExpect(jsonPath("$.voto").value(votoSim));

        verify(votoService, times(1)).registrarVoto(sessaoId, associadoId, votoSim);
    }

    @Test
    public void testObterResultado() throws Exception {
        Long sessaoId = 1L;
        long votosSim = 5L;
        long votosNao = 3L;

        when(votoService.contarVotosSim(sessaoId)).thenReturn(votosSim);
        when(votoService.contarVotosNao(sessaoId)).thenReturn(votosNao);

        mockMvc.perform(get("/api/votos/{sessaoId}/resultado", sessaoId))
                .andExpect(status().isOk())
                .andExpect(content().string("Votos Sim: 5, Votos Não: 3"));

        verify(votoService, times(1)).contarVotosSim(sessaoId);
        verify(votoService, times(1)).contarVotosNao(sessaoId);
    }
}