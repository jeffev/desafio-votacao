package com.example.desafio_votacao.controller;

import com.example.desafio_votacao.facade.CpfValidationFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cpf")
public class CpfController {

    private final CpfValidationFacade cpfValidationFacade;

    public CpfController(CpfValidationFacade cpfValidationFacade) {
        this.cpfValidationFacade = cpfValidationFacade;
    }

    @GetMapping("/validar/{cpf}")
    public ResponseEntity<String> validarCpf(@PathVariable String cpf) {
        try {
            String status = cpfValidationFacade.validarCpf(cpf);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CPF não é válido.");
        }
    }
}