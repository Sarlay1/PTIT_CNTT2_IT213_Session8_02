package org.example.hackathon_de02.config;

import org.example.hackathon_de02.tools.MovieTools;
import org.example.hackathon_de02.tools.TicketBookingTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(10)
                .build();
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder,
                                 MovieTools movieTools,
                                 TicketBookingTool ticketBookingTool) {
        return chatClientBuilder
                .defaultSystem("Bạn là trợ lý AI của CinemaStar. Hãy dùng tool khi cần tra cứu phim, thông tin rạp hoặc đặt vé.")
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory()).build()
                )
                .defaultTools(movieTools, ticketBookingTool)
                .build();
    }
}
