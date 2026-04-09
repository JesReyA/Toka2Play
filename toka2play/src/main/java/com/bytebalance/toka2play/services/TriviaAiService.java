package com.bytebalance.toka2play.services;

import com.bytebalance.toka2play.models.TriviaResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class TriviaAiService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TriviaResponse generarPreguntasConIA(String categoria, int cantidad, String dificultad) {
        RestTemplate restTemplate = new RestTemplate();

        String prompt = "Genera exactamente " + cantidad + " preguntas de trivia sobre " + categoria +
                " con dificultad " + dificultad + "." +
                " Responde ÚNICAMENTE con un JSON válido con este formato exacto, sin texto adicional ni backticks:" +
                " { \"questions\": [ { \"pregunta\": \"...\", \"opciones\": [\"opcion1\", \"opcion2\", \"opcion3\", \"opcion4\"], \"correcta\": 0 } ] }"
                +
                " Donde 'correcta' es el índice (0-3) de la opción correcta dentro del arreglo 'opciones'.";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of("role", "system", "content",
                                "Eres un generador de trivias. Respondes ÚNICAMENTE con JSON válido, sin explicaciones ni backticks."),
                        Map.of("role", "user", "content", prompt)),
                "temperature", 0.7,
                "max_tokens", 4096,
                "response_format", Map.of("type", "json_object"));

        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            String rawBody = restTemplate.postForObject(API_URL, entity, String.class);

            // Groq usa formato OpenAI: choices[0].message.content
            JsonNode root = objectMapper.readTree(rawBody);
            String triviaJson = root
                    .path("choices").get(0)
                    .path("message")
                    .path("content").asText();

            return objectMapper.readValue(triviaJson, TriviaResponse.class);

        } catch (Exception e) {
            throw new RuntimeException("Error al generar preguntas con IA: " + e.getMessage(), e);
        }
    }

    @org.springframework.beans.factory.annotation.Autowired
    private com.bytebalance.toka2play.repository.TriviaRepository triviaRepository;

    public Map<String, Object> generarTriviaSolo(String nombre, String categoria, int cantidad, String dificultad) {
        TriviaResponse triviaResponse = generarPreguntasConIA(categoria, cantidad, dificultad);
        String idTrivia = generarCodigoSala();

        return Map.of(
                "codigo", idTrivia,
                "questions", triviaResponse.questions,
                "nombre", nombre);
    }

    public void guardarTrivia(String idTrivia, String nombre, List<TriviaResponse.Question> questions) {
        List<com.bytebalance.toka2play.models.Pregunta> preguntas = questions.stream().map(q -> {
            List<com.bytebalance.toka2play.models.Respuesta> respuestas = new java.util.ArrayList<>();
            for (int i = 0; i < q.opciones.size(); i++) {
                respuestas.add(new com.bytebalance.toka2play.models.Respuesta(q.opciones.get(i), i == q.correcta));
            }
            return new com.bytebalance.toka2play.models.Pregunta(q.pregunta, respuestas);
        }).collect(java.util.stream.Collectors.toList());

        com.bytebalance.toka2play.models.Trivia trivia = new com.bytebalance.toka2play.models.Trivia(idTrivia, nombre,
                preguntas);
        triviaRepository.save(trivia);
    }

    private String generarCodigoSala() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder("#");
        for (int i = 0; i < 6; i++) {
            if (i == 3)
                sb.append("-");
            sb.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return sb.toString();
    }

}
