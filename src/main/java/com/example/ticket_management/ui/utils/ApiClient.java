package com.example.ticket_management.ui.utils;

import com.example.ticket_management.dto.TicketDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static TicketDTO createTicket(TicketDTO ticket) throws IOException, InterruptedException {
        String endpoint = BASE_URL + "/tickets";
        String json = MAPPER.writeValueAsString(ticket);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status HTTP: " + response.statusCode());
        System.out.println("Body: " + response.body());

        if (response.statusCode() == 201) {
            return MAPPER.readValue(response.body(), TicketDTO.class);
        }
        throw new RuntimeException("Error creating ticket: " + response.statusCode() + " " + response.body());
    }


    public static List<TicketDTO> getAllTickets() throws IOException, InterruptedException {
        String endpoint = BASE_URL + "/tickets";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return MAPPER.readValue(response.body(), new TypeReference<List<TicketDTO>>() {});
        }
        throw new RuntimeException("Error fetching tickets: " + response.statusCode());
    }

    public static TicketDTO getTicketById(Long id) throws IOException, InterruptedException {
        String endpoint = BASE_URL + "/tickets/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return MAPPER.readValue(response.body(), TicketDTO.class);
        }
        throw new RuntimeException("Ticket not found: " + response.statusCode());
    }

    public static List<TicketDTO> getTicketsByStatus(String status) throws IOException, InterruptedException {
        String endpoint = BASE_URL + "/tickets/status?status=" + status;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return MAPPER.readValue(response.body(), new TypeReference<List<TicketDTO>>() {});
        }
        throw new RuntimeException("Error fetching tickets by status: " + response.statusCode());
    }

    public static TicketDTO updateTicketStatus(Long id, String newStatus) throws IOException, InterruptedException {
        String endpoint = BASE_URL + "/tickets/" + id + "/status?status=" + newStatus;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return MAPPER.readValue(response.body(), TicketDTO.class);
        }
        throw new RuntimeException("Error updating ticket status: " + response.statusCode());
    }

    public static void addComment(Long ticketId, String username, String content) throws IOException, InterruptedException {
        String endpoint = BASE_URL + "/comments/" + ticketId + "?username=" + username;
        CommentRequest commentRequest = new CommentRequest(content);
        String json = MAPPER.writeValueAsString(commentRequest);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201) {
            throw new RuntimeException("Error adding comment: " + response.statusCode());
        }
    }
}
