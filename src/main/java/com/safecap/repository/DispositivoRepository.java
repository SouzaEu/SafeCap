package com.safecap.repository;

import com.safecap.model.Dispositivo;
import com.safecap.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
    List<Dispositivo> findByUsuario(Usuario usuario);

    @Query("SELECT d FROM Dispositivo d WHERE d.id = :id AND d.usuario = :usuario AND d.deleted = false")
    Optional<Dispositivo> findByIdAndUsuario(@Param("id") Long id, @Param("usuario") Usuario usuario);
}
