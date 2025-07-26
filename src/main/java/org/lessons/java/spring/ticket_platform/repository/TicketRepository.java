package org.lessons.java.spring.ticket_platform.repository;

import java.util.List;

import org.lessons.java.spring.ticket_platform.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByOperatorId(int id);
    public List<Ticket> findByTitleContainingIgnoreCase(String title);
}
