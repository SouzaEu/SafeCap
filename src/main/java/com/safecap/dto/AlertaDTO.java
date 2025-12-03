package com.safecap.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AlertaDTO {
    @NotNull(message = "Temperatura é obrigatória")
    @Min(value = -10, message = "Temperatura muito baixa")
    @Max(value = 80, message = "Temperatura muito alta")
    private Double temperatura;

    @NotNull(message = "Umidade é obrigatória")
    @Min(value = 0, message = "Umidade mínima é 0%")
    @Max(value = 100, message = "Umidade máxima é 100%")
    private Double umidade;

    @NotNull(message = "ID do dispositivo é obrigatório")
    private Long dispositivoId;

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

    public Long getDispositivoId() {
        return dispositivoId;
    }

    public void setDispositivoId(Long dispositivoId) {
        this.dispositivoId = dispositivoId;
    }
}
