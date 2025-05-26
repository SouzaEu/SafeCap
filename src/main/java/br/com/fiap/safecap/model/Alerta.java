
package br.com.fiap.safecap.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Alerta extends AuditableEntity { Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double temperatura;
    private Double umidade;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    private Dispositivo dispositivo;

    // Getters e setters
}
