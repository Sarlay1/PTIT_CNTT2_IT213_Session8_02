package org.example.hackathon_de02.service;

import lombok.RequiredArgsConstructor;
import org.example.hackathon_de02.model.constant.TicketBookingStatus;
import org.example.hackathon_de02.model.entity.Movie;
import org.example.hackathon_de02.model.entity.TicketBooking;
import org.example.hackathon_de02.model.entity.TicketItem;
import org.example.hackathon_de02.model.entity.Viewer;
import org.example.hackathon_de02.repository.MovieRepository;
import org.example.hackathon_de02.repository.TicketBookingRepository;
import org.example.hackathon_de02.repository.TicketItemRepository;
import org.example.hackathon_de02.repository.ViewerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketBookingService {

    private final MovieRepository movieRepository;
    private final ViewerRepository viewerRepository;
    private final TicketBookingRepository ticketBookingRepository;
    private final TicketItemRepository ticketItemRepository;

    @Transactional
    public String bookTicket(String movieName, String phone, int quantity) {
        if (quantity <= 0) {
            return "Số lượng vé phải lớn hơn 0.";
        }

        Viewer viewer = viewerRepository.findByPhone(phone).orElse(null);
        if (viewer == null) {
            return "Không tìm thấy khách hàng với số điện thoại: " + phone;
        }

        Movie movie = movieRepository.findByNameContainingIgnoreCase(movieName)
                .stream()
                .findFirst()
                .orElse(null);
        if (movie == null) {
            return "Không tìm thấy phim: " + movieName;
        }

        if (movie.getStock() < quantity) {
            return "Phim " + movie.getName() + " chỉ còn " + movie.getStock() + " vé.";
        }

        BigDecimal total = movie.getPrice().multiply(BigDecimal.valueOf(quantity));

        TicketBooking booking = new TicketBooking();
        booking.setViewer(viewer);
        booking.setTicketBookingDate(LocalDateTime.now());
        booking.setStatus(TicketBookingStatus.PENDING_PAYMENT);
        booking.setTotalAmount(total);
        booking.setNote("Đặt vé qua AI Tool");
        booking = ticketBookingRepository.save(booking);

        TicketItem item = new TicketItem();
        item.setTicketBooking(booking);
        item.setMovie(movie);
        item.setQuantity(quantity);
        item.setUnitPrice(movie.getPrice());
        ticketItemRepository.save(item);

        movie.setStock(movie.getStock() - quantity);
        movieRepository.save(movie);

        return String.format("Đặt vé thành công. Mã đơn: %d | Phim: %s | Số lượng: %d | Tổng tiền: %s | Trạng thái: %s",
                booking.getId(), movie.getName(), quantity, total, booking.getStatus());
    }
}
