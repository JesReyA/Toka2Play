package com.bytebalance.toka2play.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bytebalance.toka2play.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // Al no existir columna 'nivel' en BD, calculamos base al record de la tabla puntajes
    // Suma de puntajes / 1000 + 1. O si la suma es nula, nivel 1.
    @Query(value = "SELECT COALESCE(FLOOR(SUM(puntaje) / 1000) + 1, 1) FROM puntajes WHERE idUsuario = :idUsuario", nativeQuery = true)
    int calcularNivel(@Param("idUsuario") int idUsuario);
}
