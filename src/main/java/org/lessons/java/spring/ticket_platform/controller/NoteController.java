package org.lessons.java.spring.ticket_platform.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.lessons.java.spring.ticket_platform.model.Note;
import org.lessons.java.spring.ticket_platform.model.Ticket;
import org.lessons.java.spring.ticket_platform.model.User;
import org.lessons.java.spring.ticket_platform.repository.NoteRepository;
import org.lessons.java.spring.ticket_platform.repository.TicketRepository;
import org.lessons.java.spring.ticket_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/tickets/{id}/notes")
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

    @GetMapping("/tickets/{id}/notes/create")
    public String createNoteForm(@PathVariable("id") Integer ticketId, Model model) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            return "redirect:/tickets";
        }

        Note note = new Note();
        note.setTicket(ticket);

        model.addAttribute("note", note);
        model.addAttribute("users", userRepository.findAll());
        return "notes/create";
    }

    @PostMapping("/notes/store")
    public String storeNote(@ModelAttribute Note note, @RequestParam Integer author) {
        User user = userRepository.findById(author).orElse(null);
        note.setAuthor(user);
        note.setCreationDate(LocalDateTime.now());
        noteRepository.save(note);
        return "redirect:/tickets/" + note.getTicket().getId() + "/notes";
    }

}
