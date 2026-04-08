package com.bytebalance.toka2play.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TriviaResponse {
    public List<Question> questions;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Question {
        public String pregunta;
        public List<String> opciones;
        public int correcta;
    }
}
