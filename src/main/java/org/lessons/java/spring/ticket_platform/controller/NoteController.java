package org.lessons.java.spring.ticket_platform.controller;

import org.lessons.java.spring.ticket_platform.model.Note;
import org.lessons.java.spring.ticket_platform.model.Ticket;
import org.lessons.java.spring.ticket_platform.repository.NoteRepository;
import org.lessons.java.spring.ticket_platform.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/{id}/notes")
    public String getNotesByTicket(@PathVariable("id") Integer ticketId, Model model) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            return "redirect:/tickets"; 
        }

        List<Note> notes = noteRepository.findByTicketId(ticketId);
        model.addAttribute("ticket", ticket);
        model.addAttribute("notes", notes);

        return "notes/index";
    }
}
