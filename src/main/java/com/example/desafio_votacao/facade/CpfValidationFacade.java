package com.example.desafio_votacao.facade;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class CpfValidationFacade {

    // Método para validar o CPF e retornar o status do voto
    public String validarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido");
        }

        Random random = new Random();
        int chance = random.nextInt(2);

        if (chance == 0) {
            return "UNABLE_TO_VOTE";
        } else {
            return "ABLE_TO_VOTE";
        }
    }
}
