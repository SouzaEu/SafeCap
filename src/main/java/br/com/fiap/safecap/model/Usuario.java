
package br.com.fiap.safecap.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Usuario extends AuditableEntity { Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    private String role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Dispositivo> dispositivos;

    // Getters e setters
}
