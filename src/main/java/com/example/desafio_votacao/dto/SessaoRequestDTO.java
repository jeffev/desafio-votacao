package com.example.desafio_votacao.dto;

public class SessaoRequestDTO {
    private Long pautaId;
    private Integer duracaoEmMinutos;

    // Getters e Setters
    public Long getPautaId() {
        return pautaId;
    }

    public void setPautaId(Long pautaId) {
        this.pautaId = pautaId;
    }

    public Integer getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public void setDuracaoEmMinutos(Integer duracaoEmMinutos) {
        this.duracaoEmMinutos = duracaoEmMinutos;
    }
}
