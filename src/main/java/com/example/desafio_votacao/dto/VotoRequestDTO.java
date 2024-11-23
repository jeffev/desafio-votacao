package com.example.desafio_votacao.dto;

public class VotoRequestDTO {

    private Long sessaoId;
    private String associadoId;
    private boolean votoSim;

    // Getters e Setters
    public Long getSessaoId() {
        return sessaoId;
    }

    public void setSessaoId(Long sessaoId) {
        this.sessaoId = sessaoId;
    }

    public String getAssociadoId() {
        return associadoId;
    }

    public void setAssociadoId(String associadoId) {
        this.associadoId = associadoId;
    }

    public boolean isVotoSim() {
        return votoSim;
    }

    public void setVotoSim(boolean votoSim) {
        this.votoSim = votoSim;
    }
}
