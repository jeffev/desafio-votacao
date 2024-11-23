package com.example.desafio_votacao.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Long resourceId) {
        super(String.format("%s não encontrado(a) com o ID: %d", resourceName, resourceId));
    }
}
