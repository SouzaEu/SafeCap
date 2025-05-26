
package br.com.fiap.safecap.repository;

import br.com.fiap.safecap.model.Alerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    @Query("SELECT a FROM Alerta a " +
           "WHERE (:temperaturaMin IS NULL OR a.temperatura >= :temperaturaMin) " +
           "AND (:umidadeMax IS NULL OR a.umidade <= :umidadeMax) " +
           "AND a.deleted = false")
    Page<Alerta> findByFiltros(@Param("temperaturaMin") Double temperaturaMin,
                                @Param("umidadeMax") Double umidadeMax,
                                Pageable pageable);
}
