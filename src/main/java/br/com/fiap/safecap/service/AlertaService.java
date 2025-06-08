package br.com.fiap.safecap.service;

import br.com.fiap.safecap.model.Alerta;
import br.com.fiap.safecap.model.Usuario;
import br.com.fiap.safecap.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    public Alerta save(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    public List<Alerta> findByUsuario(Usuario usuario) {
        return alertaRepository.findByDispositivoUsuario(usuario);
    }

    public Optional<Alerta> findByIdAndUsuario(Long id, Usuario usuario) {
        return alertaRepository.findByIdAndDispositivoUsuario(id, usuario);
    }

    public Page<Alerta> findByUsuarioWithFilters(
            Usuario usuario,
            LocalDateTime dataInicial,
            LocalDateTime dataFinal,
            Double temperaturaMin,
            Double temperaturaMax,
            Double umidadeMin,
            Double umidadeMax,
            Pageable pageable) {
        
        return alertaRepository.findByUsuarioWithFilters(
            usuario,
            dataInicial,
            dataFinal,
            temperaturaMin,
            temperaturaMax,
            umidadeMin,
            umidadeMax,
            pageable
        );
    }
}
