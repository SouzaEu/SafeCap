package br.com.fiap.safecap.service;

import br.com.fiap.safecap.model.Alerta;
import br.com.fiap.safecap.model.Usuario;
import br.com.fiap.safecap.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Profile("!nodb")
@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;

    @Autowired
    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public Alerta save(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    public List<Alerta> findByUsuario(Usuario usuario) {
        return alertaRepository.findByUsuario(usuario);
    }

    public Optional<Alerta> findByIdAndUsuario(Long id, Usuario usuario) {
        return alertaRepository.findByIdAndUsuario(id, usuario);
    }
}
