package br.com.fiap.safecap.service;

import br.com.fiap.safecap.model.Alerta;
import br.com.fiap.safecap.repository.AlertaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public Page<Alerta> findWithFilters(Pageable pageable, Double temperaturaMin, Double umidadeMax) {
        List<Alerta> todos = alertaRepository.findAll();

        List<Alerta> filtrados = todos.stream()
            .filter(a -> (temperaturaMin == null || a.getTemperatura() > temperaturaMin) &&
                         (umidadeMax == null || a.getUmidade() < umidadeMax))
            .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtrados.size());
        List<Alerta> pageContent = filtrados.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filtrados.size());
    }

    public Alerta save(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    public void deleteById(Long id) {
        alertaRepository.deleteById(id);
    }
}
