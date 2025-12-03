package com.safecap.service;

import com.safecap.exception.BusinessRuleException;
import com.safecap.model.Usuario;
import com.safecap.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        logger.info("Salvando novo usuário: {}", usuario.getEmail());
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            logger.warn("Tentativa de registro com email já existente: {}", usuario.getEmail());
            throw new BusinessRuleException("Email já cadastrado");
        }
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Realizando exclusão suave do usuário: {}", id);
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setDeleted(true);
            usuarioRepository.save(usuario);
            logger.info("Usuário {} marcado como excluído", id);
        });
    }
}
