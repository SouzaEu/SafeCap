package br.com.fiap.safecap.repository;

import br.com.fiap.safecap.model.Dispositivo;
import br.com.fiap.safecap.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
    List<Dispositivo> findByUsuario(Usuario usuario);

    @Query("SELECT d FROM Dispositivo d WHERE d.id = :id AND d.usuario = :usuario AND d.deleted = false")
    Optional<Dispositivo> findByIdAndUsuario(@Param("id") Long id, @Param("usuario") Usuario usuario);
}
