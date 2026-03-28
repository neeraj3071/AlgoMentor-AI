package com.dsavisualizer.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class AITutorService {

    @Value("${gemini.model:gemini-2.0-flash}")
    private String model;

    @Value("${gemini.api-key}")
    private String geminiApiKey;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String explainCode(String code) {
        String prompt = "Explain this Java code clearly for an interview prep student. " +
                "Mention approach, edge cases, and time/space complexity.\\n\\nCode:\\n" + code;
        return callGeminiAPI(prompt, "Code explanation is currently unavailable.");
    }

    public String getHint(String problemDescription) {
        String prompt = "Give one concise hint (not full solution) for this DSA problem:\\n" + problemDescription;
        return callGeminiAPI(prompt, "Hint service is currently unavailable.");
    }

    public String analyzeComplexity(String code) {
        String prompt = "Analyze this Java code and return only time and space complexity with brief reasoning:\\n"
                + code;
        return callGeminiAPI(prompt, "Complexity analysis is currently unavailable.");
    }

    public String suggestOptimization(String code, String problemDescription) {
        String prompt = "Suggest practical optimizations for this Java solution to the problem below. " +
                "Return a short list with tradeoffs.\\nProblem:\\n" + problemDescription + "\\n\\nCode:\\n" + code;
        return callGeminiAPI(prompt, "Optimization suggestion is currently unavailable.");
    }

    private String callGeminiAPI(String prompt, String fallbackMessage) {
        if (geminiApiKey == null || geminiApiKey.isBlank() || geminiApiKey.startsWith("your-")) {
            return fallbackMessage + " Configure GEMINI_API_KEY to enable this feature.";
        }

        try {
            String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/" + model
                    + ":generateContent?key=" + geminiApiKey;

            JsonObject payload = new JsonObject();
            JsonArray contents = new JsonArray();
            JsonObject content = new JsonObject();
            JsonArray parts = new JsonArray();
            JsonObject part = new JsonObject();
            part.addProperty("text", prompt);
            parts.add(part);
            content.add("parts", parts);
            contents.add(content);
            payload.add("contents", contents);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .timeout(Duration.ofSeconds(30))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                return fallbackMessage;
            }

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            if (!json.has("candidates") || json.getAsJsonArray("candidates").isEmpty()) {
                return fallbackMessage;
            }

            JsonObject candidate = json.getAsJsonArray("candidates").get(0).getAsJsonObject();
            JsonObject contentObj = candidate.getAsJsonObject("content");
            JsonArray partsArr = contentObj.getAsJsonArray("parts");
            if (partsArr == null || partsArr.isEmpty()) {
                return fallbackMessage;
            }

            JsonObject firstPart = partsArr.get(0).getAsJsonObject();
            return firstPart.has("text") ? firstPart.get("text").getAsString() : fallbackMessage;
        } catch (IOException | InterruptedException | IllegalStateException ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return fallbackMessage;
        }
    }
}
