package org.lessons.java.spring.ticket_platform.repository;

import java.util.List;

import org.lessons.java.spring.ticket_platform.model.Note;
import org.lessons.java.spring.ticket_platform.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    
    List<Note> findByTicketId(int ticketId);

    List<Note> findByTicket(Ticket ticket);
}
