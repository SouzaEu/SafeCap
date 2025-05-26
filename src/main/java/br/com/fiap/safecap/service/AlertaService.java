
package br.com.fiap.safecap.service;

import br.com.fiap.safecap.model.Alerta;
import br.com.fiap.safecap.repository.AlertaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlertaService {

    private static final Logger logger = LoggerFactory.getLogger(AlertaService.class);
    private final AlertaRepository alertaRepository;

    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public Alerta save(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    public Page<Alerta> findWithFilters(Pageable pageable, Double temperaturaMin, Double umidadeMax) {
        return alertaRepository.findByFiltros(temperaturaMin, umidadeMax, pageable);
    }

    public Optional<Alerta> findById(Long id) {
        return alertaRepository.findById(id);
    }

    public void deleteById(Long id) {
        alertaRepository.deleteById(id);
    }
}
