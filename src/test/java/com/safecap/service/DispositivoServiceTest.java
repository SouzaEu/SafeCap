package com.safecap.service;

import com.safecap.exception.BusinessRuleException;
import com.safecap.model.Dispositivo;
import com.safecap.model.Usuario;
import com.safecap.repository.DispositivoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DispositivoServiceTest {

    @Mock
    private DispositivoRepository dispositivoRepository;

    @InjectMocks
    private DispositivoService dispositivoService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
    }

    @Test
    public void deveLancarExcecaoQuandoNomeJaExiste() {
        Dispositivo novo = new Dispositivo();
        novo.setNome("Sensor Boné 01");
        novo.setUsuario(usuario);

        Dispositivo existente = new Dispositivo();
        existente.setNome("Sensor Boné 01");
        existente.setUsuario(usuario);

        when(dispositivoRepository.findByUsuario(usuario)).thenReturn(List.of(existente));

        assertThrows(BusinessRuleException.class, () -> dispositivoService.save(novo));
    }
}
