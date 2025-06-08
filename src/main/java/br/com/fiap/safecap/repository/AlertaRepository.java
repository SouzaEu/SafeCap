package br.com.fiap.safecap.repository;

import br.com.fiap.safecap.model.Alerta;
import br.com.fiap.safecap.model.Usuario;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("!nodb")
@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    @Query("SELECT a FROM Alerta a WHERE a.dispositivo.usuario = :usuario")
    List<Alerta> findByUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT a FROM Alerta a WHERE a.id = :id AND a.dispositivo.usuario = :usuario")
    Optional<Alerta> findByIdAndUsuario(@Param("id") Long id, @Param("usuario") Usuario usuario);
}
