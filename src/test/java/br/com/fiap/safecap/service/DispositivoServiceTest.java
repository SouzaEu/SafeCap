
package br.com.fiap.safecap.service;

import br.com.fiap.safecap.exception.BusinessRuleException;
import br.com.fiap.safecap.model.Dispositivo;
import br.com.fiap.safecap.repository.DispositivoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DispositivoServiceTest {

    private DispositivoRepository dispositivoRepository;
    private DispositivoService dispositivoService;

    @BeforeEach
    public void setup() {
        dispositivoRepository = mock(DispositivoRepository.class);
        dispositivoService = new DispositivoService() {{
            dispositivoRepository = DispositivoServiceTest.this.dispositivoRepository;
        }};
    }

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
