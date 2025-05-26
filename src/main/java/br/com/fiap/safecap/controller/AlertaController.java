
package br.com.fiap.safecap.controller;

import br.com.fiap.safecap.dto.AlertaDTO;
import br.com.fiap.safecap.model.Alerta;
import br.com.fiap.safecap.model.Dispositivo;
import br.com.fiap.safecap.service.AlertaService;
import br.com.fiap.safecap.service.DispositivoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final AlertaService alertaService;
    private final DispositivoService dispositivoService;

    @Autowired
    public AlertaController(AlertaService alertaService, DispositivoService dispositivoService) {
        this.alertaService = alertaService;
        this.dispositivoService = dispositivoService;
    }

    @Operation(summary = "Cria um novo alerta de temperatura e umidade")
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody AlertaDTO dto) {
        Dispositivo dispositivo = dispositivoService.findById(dto.getDispositivoId())
            .orElse(null);

        if (dispositivo == null) {
            return ResponseEntity.badRequest().body("Dispositivo não encontrado");
        }

        Alerta alerta = new Alerta();
        alerta.setTemperatura(dto.getTemperatura());
        alerta.setUmidade(dto.getUmidade());
        alerta.setTimestamp(dto.getTimestamp() != null ? dto.getTimestamp() : LocalDateTime.now());
        alerta.setDispositivo(dispositivo);

        return ResponseEntity.ok(alertaService.save(alerta));
    }

    @Operation(summary = "Lista alertas com filtros e paginação")
    @GetMapping
    public ResponseEntity<Page<Alerta>> listarPaginado(
            @RequestParam(required = false) Double temperaturaMin,
            @RequestParam(required = false) Double umidadeMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(alertaService.findWithFilters(pageable, temperaturaMin, umidadeMax));
    }

    @Operation(summary = "Deleta um alerta por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alertaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
