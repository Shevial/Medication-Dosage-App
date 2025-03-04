package com.example.dto;

public record ChatMessage(String username, String body) {
    public String getUsername() { return username; }
    public String getBody() { return body; }
}
