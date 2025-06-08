package br.com.fiap.safecap.service;

import br.com.fiap.safecap.model.Alerta;
import br.com.fiap.safecap.model.Dispositivo;
import br.com.fiap.safecap.model.Usuario;
import br.com.fiap.safecap.repository.AlertaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertaServiceTest {

    @Mock
    private AlertaRepository alertaRepository;

    @InjectMocks
    private AlertaService alertaService;

    private Usuario usuario;
    private Dispositivo dispositivo;
    private Alerta alerta;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@example.com");

        dispositivo = new Dispositivo();
        dispositivo.setId(1L);
        dispositivo.setUsuario(usuario);

        alerta = new Alerta();
        alerta.setId(1L);
        alerta.setDispositivo(dispositivo);
        alerta.setTemperatura(25.0);
        alerta.setUmidade(60.0);
    }

    @Test
    void save_ShouldReturnSavedAlerta() {
        when(alertaRepository.save(any(Alerta.class))).thenReturn(alerta);

        Alerta savedAlerta = alertaService.save(alerta);

        assertNotNull(savedAlerta);
        assertEquals(alerta.getId(), savedAlerta.getId());
        verify(alertaRepository).save(alerta);
    }

    @Test
    void findByUsuario_ShouldReturnListOfAlertas() {
        List<Alerta> alertas = Arrays.asList(alerta);
        when(alertaRepository.findByDispositivoUsuario(usuario)).thenReturn(alertas);

        List<Alerta> result = alertaService.findByUsuario(usuario);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(alerta.getId(), result.get(0).getId());
        verify(alertaRepository).findByDispositivoUsuario(usuario);
    }

    @Test
    void findByIdAndUsuario_ShouldReturnAlerta() {
        when(alertaRepository.findByIdAndDispositivoUsuario(alerta.getId(), usuario))
                .thenReturn(Optional.of(alerta));

        Optional<Alerta> result = alertaService.findByIdAndUsuario(alerta.getId(), usuario);

        assertTrue(result.isPresent());
        assertEquals(alerta.getId(), result.get().getId());
        verify(alertaRepository).findByIdAndDispositivoUsuario(alerta.getId(), usuario);
    }

    @Test
    void findByIdAndUsuario_ShouldReturnEmpty_WhenAlertaNotFound() {
        when(alertaRepository.findByIdAndDispositivoUsuario(alerta.getId(), usuario))
                .thenReturn(Optional.empty());

        Optional<Alerta> result = alertaService.findByIdAndUsuario(alerta.getId(), usuario);

        assertTrue(result.isEmpty());
        verify(alertaRepository).findByIdAndDispositivoUsuario(alerta.getId(), usuario);
    }
}
