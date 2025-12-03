
package com.safecap.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiError {
    private int status;
    private String mensagem;
    private LocalDateTime timestamp;
    private Map<String, String> erros;

    public ApiError(int status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(int status, String mensagem, Map<String, String> erros) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = LocalDateTime.now();
        this.erros = erros;
    }

    public int getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getErros() {
        return erros;
    }
}
