package com.bytebalance.toka2play.repository;

import com.bytebalance.toka2play.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface PuntajeRepository extends JpaRepository<Usuario, Integer> {
    @Query(value = "SELECT u.nombre as nombre, p.puntaje as puntaje " +
            "FROM puntajes p " +
            "INNER JOIN usuario u ON p.idUsuario = u.idUsuario " +
            "WHERE p.idJuego = :idJuego " +
            "ORDER BY p.puntaje DESC LIMIT 10", nativeQuery = true)
    List<Map<String, Object>> findTop10ByJuego(@Param("idJuego") int idJuego);
}