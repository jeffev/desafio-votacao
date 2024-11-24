package com.example.desafio_votacao.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.desafio_votacao.exception.ResourceNotFoundException;
import com.example.desafio_votacao.exception.ValidationException;
import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.repository.SessaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;

class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private SessaoService sessaoService;

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
    void testAbrirSessaoComSucesso() {
        when(pautaService.buscarPautaPorId(1L)).thenReturn(pauta);
        when(sessaoRepository.existsByPautaIdAndFimAfter(1L, LocalDateTime.now())).thenReturn(false);

        Sessao sessaoEsperada = new Sessao();
        sessaoEsperada.setPauta(pauta);
        sessaoEsperada.setInicio(LocalDateTime.now());
        sessaoEsperada.setFim(LocalDateTime.now().plusMinutes(30));

        when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessaoEsperada);

        Sessao sessao = sessaoService.abrirSessao(1L, 30);

        assertNotNull(sessao);
        assertEquals(pauta, sessao.getPauta());
        assertTrue(sessao.getInicio().isBefore(sessao.getFim()));
        verify(sessaoRepository, times(1)).save(any(Sessao.class));
    }

    @Test
    void testAbrirSessaoComPautaIdNull() {
        ValidationException thrown = assertThrows(ValidationException.class, () -> sessaoService.abrirSessao(null, 30));
        assertEquals("O ID é obrigatório.", thrown.getMessage());
    }

    @Test
    void testBuscarSessaoPorIdComSucesso() {
        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setPauta(pauta);
        sessao.setInicio(LocalDateTime.now());
        sessao.setFim(LocalDateTime.now().plusMinutes(30));

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));

        Sessao result = sessaoService.buscarSessaoPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(pauta, result.getPauta());
        verify(sessaoRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarSessaoPorIdNaoEncontrada() {
        when(sessaoRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> sessaoService.buscarSessaoPorId(1L));
        assertEquals("Sessão não encontrado(a) com o ID: 1", thrown.getMessage());
    }

    @Test
    void testIsSessaoAbertaComSessaoAberta() {
        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setPauta(pauta);
        sessao.setInicio(LocalDateTime.now());
        sessao.setFim(LocalDateTime.now().plusMinutes(30));

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));

        boolean result = sessaoService.isSessaoAberta(1L);

        assertTrue(result);
    }

    @Test
    void testIsSessaoAbertaComSessaoFechada() {
        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setPauta(pauta);
        sessao.setInicio(LocalDateTime.now().minusMinutes(60));
        sessao.setFim(LocalDateTime.now().minusMinutes(30));

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));

        ValidationException thrown = assertThrows(ValidationException.class, () -> sessaoService.isSessaoAberta(1L));
        assertEquals("A sessão está fechada.", thrown.getMessage());
    }

}
