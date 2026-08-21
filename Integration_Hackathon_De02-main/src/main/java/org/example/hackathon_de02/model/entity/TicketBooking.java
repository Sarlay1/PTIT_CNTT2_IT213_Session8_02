package org.example.hackathon_de02.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hackathon_de02.model.constant.TicketBookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticketBookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewer_id", nullable = false)
    private Viewer viewer;

    @Column(nullable = false)
    private LocalDateTime ticketBookingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TicketBookingStatus status;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal totalAmount;

    // Ghi chú nguồn gốc đơn hàng, ví dụ: "Đặt qua AI Chatbot"
    @Column(length = 255)
    private String note;
}
