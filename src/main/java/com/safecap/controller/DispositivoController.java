package com.safecap.controller;

import com.safecap.dto.DispositivoDTO;
import com.safecap.dto.DispositivoResponseDTO;
import com.safecap.model.Dispositivo;
import com.safecap.model.Usuario;
import com.safecap.service.DispositivoService;
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
@RequestMapping("/api/dispositivos")
@Tag(name = "Dispositivos", description = "API para gerenciamento de dispositivos")
@SecurityRequirement(name = "bearerAuth")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;

    @PostMapping
    @Operation(summary = "Criar um novo dispositivo", description = "Cria um novo dispositivo para o usuário autenticado")
    public ResponseEntity<DispositivoResponseDTO> criar(@Valid @RequestBody DispositivoDTO dispositivoDTO, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setNome(dispositivoDTO.getNome());
        dispositivo.setUsuario(usuario);

        Dispositivo dispositivoSalvo = dispositivoService.save(dispositivo);
        return ResponseEntity.ok(convertToDTO(dispositivoSalvo));
    }

    @GetMapping
    @Operation(summary = "Listar dispositivos", description = "Lista todos os dispositivos do usuário")
    public ResponseEntity<List<DispositivoResponseDTO>> listar(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        List<Dispositivo> dispositivos = dispositivoService.findByUsuario(usuario);
        List<DispositivoResponseDTO> dispositivosDTO = dispositivos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dispositivosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar dispositivo por ID", description = "Busca um dispositivo específico pelo ID")
    public ResponseEntity<DispositivoResponseDTO> buscarPorId(@PathVariable Long id, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Dispositivo dispositivo = dispositivoService.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));
        return ResponseEntity.ok(convertToDTO(dispositivo));
    }

    private DispositivoResponseDTO convertToDTO(Dispositivo dispositivo) {
        DispositivoResponseDTO dto = new DispositivoResponseDTO();
        dto.setId(dispositivo.getId());
        dto.setNome(dispositivo.getNome());
        return dto;
    }
}
