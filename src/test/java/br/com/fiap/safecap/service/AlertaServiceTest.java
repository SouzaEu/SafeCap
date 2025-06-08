package br.com.fiap.safecap.service;

import br.com.fiap.safecap.model.Alerta;
import br.com.fiap.safecap.repository.AlertaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlertaServiceTest {

    private AlertaRepository alertaRepository;
    private AlertaService alertaService;

    @BeforeEach
    public void setup() {
        alertaRepository = mock(AlertaRepository.class);
        alertaService = new AlertaService(alertaRepository);
    }

    @Test
    public void testSave() {
        Alerta alerta = new Alerta();
        alerta.setTemperatura(25.5);
        alerta.setUmidade(60.0);

        when(alertaRepository.save(any(Alerta.class))).thenReturn(alerta);

        Alerta saved = alertaService.save(alerta);

        verify(alertaRepository).save(alerta);
        assertEquals(25.5, saved.getTemperatura());
        assertEquals(60.0, saved.getUmidade());
    }
}
