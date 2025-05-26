
package br.com.fiap.safecap.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AlertaDTO {

    @NotNull
    @Min(value = -10, message = "Temperatura muito baixa")
    @Max(value = 80, message = "Temperatura muito alta")
    private Double temperatura;

    @NotNull
    @Min(value = 0, message = "Umidade mínima é 0%")
    @Max(value = 100, message = "Umidade máxima é 100%")
    private Double umidade;

    private LocalDateTime timestamp;

    private Long dispositivoId;

    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }

    public Double getUmidade() { return umidade; }
    public void setUmidade(Double umidade) { this.umidade = umidade; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Long getDispositivoId() { return dispositivoId; }
    public void setDispositivoId(Long dispositivoId) { this.dispositivoId = dispositivoId; }
}
