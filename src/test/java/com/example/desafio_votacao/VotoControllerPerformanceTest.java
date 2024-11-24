package com.example.desafio_votacao;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public class VotoControllerPerformanceTest {

    @Mock
    private MockMvc mockMvc;

    private static final long MAX_EXECUTION_TIME = 2000;  // Defina o valor máximo de tempo de execução

    @Test
    public void testRegistrarVotoPerformance() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            mockMvc.perform(post("/api/v1/votos")
                    .contentType("application/json")
                    .content("{\"sessaoId\":1, \"associadoId\":" + i + ", \"votoSim\":true}"));
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Tempo total para registrar 100.000 votos: " + duration + "ms");

        assertTrue(duration < MAX_EXECUTION_TIME, "O teste falhou porque o tempo de execução excedeu " + MAX_EXECUTION_TIME + "ms. Tempo de execução: " + duration + "ms");
    }
}