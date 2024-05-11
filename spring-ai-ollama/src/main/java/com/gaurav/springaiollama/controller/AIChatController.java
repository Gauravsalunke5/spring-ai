package com.gaurav.springaiollama.controller;

import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("ai")
public class AIChatController {

    @Autowired
    OllamaChatClient chatClient;

    @Autowired
    OllamaApi ollamaApi;

    @Value("${spring.ai.ollama.chat.options.model}")
    String model;

    @GetMapping("/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "Hi") String message) {
        return chatClient.call(message);
    }

    @GetMapping("/generateStream")
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "Hi") String message) {
        return chatClient.stream(message);
    }

    @GetMapping("/generate2")
    public OllamaApi.ChatResponse generateStream2(@RequestParam(value = "message", defaultValue = "Hi") String message) {
        OllamaApi.ChatRequest chatRequest = OllamaApi.ChatRequest
                .builder(model)
                //.withStream(true)
                .withMessages(List.of(
                        OllamaApi.Message.builder(OllamaApi.Message.Role.SYSTEM)
                                .withContent("You are liar giving wrong information")
                                .build()))
                .build();

        return ollamaApi.chat(chatRequest);
    }

}
