
package br.com.fiap.safecap.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Dispositivo extends AuditableEntity { Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "dispositivo", cascade = CascadeType.ALL)
    private List<Alerta> alertas;

    // Getters e setters
}
