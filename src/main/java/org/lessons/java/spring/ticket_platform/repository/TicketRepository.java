package org.lessons.java.spring.ticket_platform.repository;

import org.lessons.java.spring.ticket_platform.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
