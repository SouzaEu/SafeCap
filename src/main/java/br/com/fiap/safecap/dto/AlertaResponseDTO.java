package br.com.fiap.safecap.dto;

import java.time.LocalDateTime;

public class AlertaResponseDTO {
    private Long id;
    private Double temperatura;
    private Double umidade;
    private LocalDateTime timestamp;
    private DispositivoResponseDTO dispositivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getUmidade() {
        return umidade;
    }

    public void setUmidade(Double umidade) {
        this.umidade = umidade;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public DispositivoResponseDTO getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(DispositivoResponseDTO dispositivo) {
        this.dispositivo = dispositivo;
    }
} 