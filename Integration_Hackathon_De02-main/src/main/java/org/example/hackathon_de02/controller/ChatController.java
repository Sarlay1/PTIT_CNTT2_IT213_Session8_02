package org.example.hackathon_de02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {


// Ví dụ
//    @PostMapping
//    public String chat() {
//
//        return chatClient.prompt()
//                .user()
//                .advisors(a -> a.param("chat_memory_conversation_id", ))
//                .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
//                .call()
//                .content();
//    }



}
