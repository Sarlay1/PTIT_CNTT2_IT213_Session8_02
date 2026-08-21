package org.example.hackathon_de02.tools;

import lombok.RequiredArgsConstructor;
import org.example.hackathon_de02.service.TicketBookingService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketBookingTool {

    private final TicketBookingService ticketBookingService;

    @Tool(name = "bookMovieTicket", description = "Đặt vé xem phim cho khách hàng khi đã có tên phim, số điện thoại và số lượng vé.")
    public String bookMovieTicket(
            @ToolParam(description = "Tên phim") String movieName,
            @ToolParam(description = "Số điện thoại khách hàng") String phone,
            @ToolParam(description = "Số lượng vé") int quantity) {
        return ticketBookingService.bookTicket(movieName, phone, quantity);
    }
}
