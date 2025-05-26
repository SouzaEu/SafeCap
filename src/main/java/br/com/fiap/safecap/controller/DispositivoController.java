
package br.com.fiap.safecap.controller;

import br.com.fiap.safecap.model.Dispositivo;
import br.com.fiap.safecap.service.DispositivoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dispositivos")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;

    @Operation(summary = "Cria um novo dispositivo")
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Dispositivo dispositivo) {
        List<Dispositivo> dispositivos = dispositivoService.findAll();
        boolean nomeExistente = dispositivos.stream()
            .anyMatch(d -> d.getNome().equalsIgnoreCase(dispositivo.getNome()));

        if (nomeExistente) {
            return ResponseEntity.badRequest().body("Nome de dispositivo já cadastrado");
        }

        return ResponseEntity.ok(dispositivoService.save(dispositivo));
    }

    @Operation(summary = "Lista todos os dispositivos")
    @GetMapping
    public ResponseEntity<List<Dispositivo>> listar() {
        return ResponseEntity.ok(dispositivoService.findAll());
    }

    @Operation(summary = "Deleta um dispositivo por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dispositivoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
