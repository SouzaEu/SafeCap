package br.com.fiap.safecap.model;

import jakarta.persistence.*;
import java.util.List;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "DISPOSITIVO")
public class Dispositivo extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DISPOSITIVO")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "NOME", unique = true)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "dispositivo", cascade = CascadeType.ALL)
    private List<Alerta> alertas;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<Alerta> alertas) {
        this.alertas = alertas;
    }
}
