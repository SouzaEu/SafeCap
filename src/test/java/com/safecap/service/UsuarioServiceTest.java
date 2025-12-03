package com.safecap.service;

import com.safecap.exception.BusinessRuleException;
import com.safecap.model.Usuario;
import com.safecap.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    public void setup() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    public void deveLancarExcecaoQuandoEmailJaExiste() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@fiap.com");

        when(usuarioRepository.findByEmail("teste@fiap.com"))
                .thenReturn(Optional.of(new Usuario()));

        assertThrows(BusinessRuleException.class, () -> usuarioService.save(usuario));
    }
}
