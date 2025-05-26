
package br.com.fiap.safecap.service;

import br.com.fiap.safecap.exception.BusinessRuleException;
import br.com.fiap.safecap.model.Dispositivo;
import br.com.fiap.safecap.repository.DispositivoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DispositivoService {

    private static final Logger logger = LoggerFactory.getLogger(DispositivoService.class);
    private final DispositivoRepository dispositivoRepository;

    public DispositivoService(DispositivoRepository dispositivoRepository) {
        this.dispositivoRepository = dispositivoRepository;
    }

    public Dispositivo save(Dispositivo dispositivo) {
        logger.info("Salvando novo dispositivo: {}", dispositivo.getNome());
        List<Dispositivo> dispositivos = dispositivoRepository.findAll();
        boolean exists = dispositivos.stream().anyMatch(d -> d.getNome().equalsIgnoreCase(dispositivo.getNome()));
        if (exists) {
            logger.warn("Nome de dispositivo já existe: {}", dispositivo.getNome());
            throw new BusinessRuleException("Nome de dispositivo já cadastrado");
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
}
