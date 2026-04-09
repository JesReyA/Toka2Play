package com.bytebalance.toka2play.repository;

import com.bytebalance.toka2play.models.PuntajeTrivia;
import com.bytebalance.toka2play.models.PuntajeTriviaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuntajeTriviaRepository extends JpaRepository<PuntajeTrivia, PuntajeTriviaId> {
}
