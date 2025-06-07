package br.com.fiap.safecap.service;

import br.com.fiap.safecap.model.Alerta;
import br.com.fiap.safecap.repository.AlertaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public Page<Alerta> findWithFilters(Pageable pageable, Double temperaturaMin, Double umidadeMax) {
        return alertaRepository.findByFiltros(temperaturaMin, umidadeMax, pageable);
    }

    public Alerta save(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    public void deleteById(Long id) {
        alertaRepository.deleteById(id);
    }
}
