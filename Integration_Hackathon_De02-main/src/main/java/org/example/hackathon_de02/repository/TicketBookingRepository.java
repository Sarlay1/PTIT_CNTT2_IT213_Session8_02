package org.example.hackathon_de02.repository;

import org.example.hackathon_de02.model.entity.TicketBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketBookingRepository extends JpaRepository<TicketBooking, Long> {
}
