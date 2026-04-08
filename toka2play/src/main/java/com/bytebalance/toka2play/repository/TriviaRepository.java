// ruta: src/main/java/com/bytebalance/toka2play/repository/TriviaRepository.java
package com.bytebalance.toka2play.repository;

import com.bytebalance.toka2play.models.Trivia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TriviaRepository extends JpaRepository<Trivia, String> {
}