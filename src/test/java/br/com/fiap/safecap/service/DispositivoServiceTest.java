package br.com.fiap.safecap.service;

import br.com.fiap.safecap.exception.BusinessRuleException;
import br.com.fiap.safecap.model.Dispositivo;
import br.com.fiap.safecap.repository.DispositivoRepository;
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

    @Test
    public void deveLancarExcecaoQuandoNomeJaExiste() {
        Dispositivo novo = new Dispositivo();
        novo.setNome("Sensor Boné 01");

        Dispositivo existente = new Dispositivo();
        existente.setNome("Sensor Boné 01");

        when(dispositivoRepository.findAll()).thenReturn(List.of(existente));

        assertThrows(BusinessRuleException.class, () -> dispositivoService.save(novo));
    }
}
