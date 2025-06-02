package br.com.fiap.safecap.service;

import br.com.fiap.safecap.model.Alerta;
import br.com.fiap.safecap.repository.AlertaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
        Alerta a1 = new Alerta();
        a1.setTemperatura(39.5);
        a1.setUmidade(28.0);
        a1.setTimestamp(LocalDateTime.now());

        Alerta a2 = new Alerta();
        a2.setTemperatura(37.0);
        a2.setUmidade(35.0);
        a2.setTimestamp(LocalDateTime.now());

        List<Alerta> todosAlertas = List.of(a1, a2);

        Pageable pageable = PageRequest.of(0, 10);

        // Filtro: temperatura > 38.0, umidade < 30.0
        List<Alerta> filtrados = todosAlertas.stream()
            .filter(a -> a.getTemperatura() > 38.0 && a.getUmidade() < 30.0)
            .toList();

        Page<Alerta> page = new PageImpl<>(filtrados, pageable, filtrados.size());

        when(alertaRepository.findAll()).thenReturn(todosAlertas);

        Page<Alerta> result = alertaService.findWithFilters(pageable, 38.0, 30.0);

        assertEquals(1, result.getTotalElements());
        assertEquals(39.5, result.getContent().get(0).getTemperatura());
    }
}
