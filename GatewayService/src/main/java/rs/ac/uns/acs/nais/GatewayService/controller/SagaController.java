package rs.ac.uns.acs.nais.GatewayService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import rs.ac.uns.acs.nais.GatewayService.dto.CreateBlogDTO;

@RestController
@RequestMapping("/saga")
public class SagaController {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public SagaController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/createBlog")
    public Mono<ResponseEntity<String>> createBlog(@RequestBody CreateBlogDTO request) {
        WebClient webClient = webClientBuilder.build();
        System.out.println("Request JSON: " + request);

        // Convert DTO to JSON payload
        String jsonPayload = "{"
                + "\"authorId\":\"" + request.getAuthorId() + "\","
                + "\"blogId\":\"" + request.getBlogId() + "\","
                + "\"category\":\"" + request.getCategory() + "\","
                + "\"title\":\"" + request.getTitle() + "\","
                + "\"description\":\"" + request.getDescription() + "\","
                + "\"createdAt\":\"" + request.getCreatedAt() + "\","
                + "\"country\":\"" + request.getCountry() + "\""
                + "}";

        // Send request to Elasticsearch microservice
        Mono<ClientResponse> elasticSearchResponse = webClient.post()
                .uri("http://localhost:9000/elasticsearch-database-service/blogs.json/createBlog")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange();

        // Send request to relational database microservice
        Mono<ClientResponse> relationalDatabaseResponse = webClient.post()
                .uri("http://localhost:9000/relational-database-service/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange();

        // Combine responses from both microservices
        return elasticSearchResponse.flatMap(response1 -> {
            if (response1.statusCode().is2xxSuccessful()) {
                return response1.bodyToMono(String.class).flatMap(elasticResponseBody -> {
                    // Extract the 'id' from the Elasticsearch response
                    String elasticId = extractIdFromResponse(elasticResponseBody);
                    
                    return relationalDatabaseResponse.flatMap(response2 -> {
                        if (response2.statusCode().is2xxSuccessful()) {
                            return Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                                    .body("Blog successfully created in Elasticsearch and relational database."));
                        } else {
                            // Rollback Elasticsearch if relational DB fails
                            return webClient.delete()
                                    .uri("http://localhost:9000/elasticsearch-database-service/blogs.json/" + elasticId)
                                    .retrieve()
                                    .bodyToMono(Void.class)
                                    .then(Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                            .body("Failed to create blog in relational database. Rolled back Elasticsearch entry.")));
                        }
                    });
                });
            } else {
                return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Failed to create blog in Elasticsearch"));
            }
        }).onErrorResume(e -> {
            System.err.println("Error occurred during blog creation: " + e.getMessage());
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred during blog creation: " + e.getMessage()));
        });
    }

    private String extractIdFromResponse(String responseBody) {
        // Assuming the 'id' field is present in the response body as a JSON object
        // You might need to adjust the parsing logic based on the actual response format
        // For simplicity, let's assume the response is a JSON object with a field 'id'
        try {
            com.fasterxml.jackson.databind.JsonNode root = new com.fasterxml.jackson.databind.ObjectMapper().readTree(responseBody);
            return root.path("id").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract 'id' from response: " + responseBody, e);
        }
    }
}
