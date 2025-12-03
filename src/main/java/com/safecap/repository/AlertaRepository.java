package com.safecap.repository;

import com.safecap.model.Alerta;
import com.safecap.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByDispositivoUsuario(Usuario usuario);

    Optional<Alerta> findByIdAndDispositivoUsuario(Long id, Usuario usuario);

    @Query("SELECT a FROM Alerta a WHERE a.dispositivo.usuario = :usuario " +
           "AND (:dataInicial IS NULL OR a.timestamp >= :dataInicial) " +
           "AND (:dataFinal IS NULL OR a.timestamp <= :dataFinal) " +
           "AND (:temperaturaMin IS NULL OR a.temperatura >= :temperaturaMin) " +
           "AND (:temperaturaMax IS NULL OR a.temperatura <= :temperaturaMax) " +
           "AND (:umidadeMin IS NULL OR a.umidade >= :umidadeMin) " +
           "AND (:umidadeMax IS NULL OR a.umidade <= :umidadeMax)")
    Page<Alerta> findByUsuarioWithFilters(
            @Param("usuario") Usuario usuario,
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("dataFinal") LocalDateTime dataFinal,
            @Param("temperaturaMin") Double temperaturaMin,
            @Param("temperaturaMax") Double temperaturaMax,
            @Param("umidadeMin") Double umidadeMin,
            @Param("umidadeMax") Double umidadeMax,
            Pageable pageable);
}
