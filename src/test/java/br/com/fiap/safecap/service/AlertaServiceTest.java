package br.com.fiap.safecap.service;

import br.com.fiap.safecap.model.Alerta;
import br.com.fiap.safecap.repository.AlertaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public void testFindWithFilters() {
        Alerta alerta = new Alerta();
        alerta.setTemperatura(39.5);
        alerta.setUmidade(28.0);
        alerta.setTimestamp(LocalDateTime.now());

        Pageable pageable = PageRequest.of(0, 10);

        Page<Alerta> page = new PageImpl<>(List.of(alerta), pageable, 1);

        when(alertaRepository.findByFiltros(38.0, 30.0, pageable)).thenReturn(page);

        Page<Alerta> result = alertaService.findWithFilters(pageable, 38.0, 30.0);

        verify(alertaRepository).findByFiltros(38.0, 30.0, pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals(39.5, result.getContent().get(0).getTemperatura());
    }
}
