package com.safecap.service;

import com.safecap.exception.BusinessRuleException;
import com.safecap.model.Dispositivo;
import com.safecap.model.Usuario;
import com.safecap.repository.DispositivoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DispositivoService {

    private static final Logger logger = LoggerFactory.getLogger(DispositivoService.class);
    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Transactional
    public Dispositivo save(Dispositivo dispositivo) {
        logger.info("Salvando novo dispositivo: {}", dispositivo.getNome());
        List<Dispositivo> dispositivos = dispositivoRepository.findByUsuario(dispositivo.getUsuario());
        boolean exists = dispositivos.stream()
            .anyMatch(d -> d.getNome().equalsIgnoreCase(dispositivo.getNome()));
        if (exists) {
            logger.warn("Nome de dispositivo já existe para este usuário: {}", dispositivo.getNome());
            throw new BusinessRuleException("Nome de dispositivo já cadastrado para este usuário");
        }
        return dispositivoRepository.save(dispositivo);
    }

    public List<Dispositivo> findAll() {
        return dispositivoRepository.findAll();
    }

    public Optional<Dispositivo> findById(Long id) {
        return dispositivoRepository.findById(id);
    }

    public void deleteById(Long id) {
        dispositivoRepository.deleteById(id);
    }

    public List<Dispositivo> findByUsuario(Usuario usuario) {
        return dispositivoRepository.findByUsuario(usuario);
    }

    public Optional<Dispositivo> findByIdAndUsuario(Long id, Usuario usuario) {
        return dispositivoRepository.findByIdAndUsuario(id, usuario);
    }
}
