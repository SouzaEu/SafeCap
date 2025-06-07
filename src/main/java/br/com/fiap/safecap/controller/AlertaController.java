package br.com.fiap.safecap.controller;

import br.com.fiap.safecap.dto.AlertaDTO;
import br.com.fiap.safecap.dto.AlertaResponseDTO;
import br.com.fiap.safecap.dto.DispositivoResponseDTO;
import br.com.fiap.safecap.model.Alerta;
import br.com.fiap.safecap.model.Dispositivo;
import br.com.fiap.safecap.model.Usuario;
import br.com.fiap.safecap.service.AlertaService;
import br.com.fiap.safecap.service.DispositivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alertas")
@Tag(name = "Alertas", description = "API para gerenciamento de alertas")
@SecurityRequirement(name = "bearerAuth")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private DispositivoService dispositivoService;

    @PostMapping
    @Operation(summary = "Criar um novo alerta", description = "Cria um novo alerta para um dispositivo")
    public ResponseEntity<AlertaResponseDTO> criar(@Valid @RequestBody AlertaDTO alertaDTO, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Dispositivo dispositivo = dispositivoService.findByIdAndUsuario(alertaDTO.getDispositivoId(), usuario)
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));

        Alerta alerta = new Alerta();
        alerta.setTemperatura(alertaDTO.getTemperatura());
        alerta.setUmidade(alertaDTO.getUmidade());
        alerta.setDispositivo(dispositivo);

        Alerta alertaSalvo = alertaService.save(alerta);
        return ResponseEntity.ok(convertToDTO(alertaSalvo));
    }

    @GetMapping
    @Operation(summary = "Listar alertas", description = "Lista todos os alertas do usuário")
    public ResponseEntity<List<AlertaResponseDTO>> listar(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        List<Alerta> alertas = alertaService.findByUsuario(usuario);
        List<AlertaResponseDTO> alertasDTO = alertas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(alertasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar alerta por ID", description = "Busca um alerta específico pelo ID")
    public ResponseEntity<AlertaResponseDTO> buscarPorId(@PathVariable Long id, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Alerta alerta = alertaService.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));
        return ResponseEntity.ok(convertToDTO(alerta));
    }

    private AlertaResponseDTO convertToDTO(Alerta alerta) {
        AlertaResponseDTO dto = new AlertaResponseDTO();
        dto.setId(alerta.getId());
        dto.setTemperatura(alerta.getTemperatura());
        dto.setUmidade(alerta.getUmidade());
        dto.setTimestamp(alerta.getTimestamp());

        DispositivoResponseDTO dispositivoDTO = new DispositivoResponseDTO();
        dispositivoDTO.setId(alerta.getDispositivo().getId());
        dispositivoDTO.setNome(alerta.getDispositivo().getNome());
        dto.setDispositivo(dispositivoDTO);

        return dto;
    }
}
