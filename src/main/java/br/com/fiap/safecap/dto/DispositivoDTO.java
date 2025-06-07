package br.com.fiap.safecap.dto;

import jakarta.validation.constraints.NotBlank;

public class DispositivoDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
} 