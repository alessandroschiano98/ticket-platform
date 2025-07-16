package org.lessons.java.spring.ticket_platform.controller;

import org.lessons.java.spring.ticket_platform.model.Ticket;
import org.lessons.java.spring.ticket_platform.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping({ "", "/" })
    public String index(Model model) {
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        return "tickets/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        Ticket ticket = ticketRepository.findById(id).get();
        model.addAttribute("ticket", ticket);
        return "tickets/show";
    }

}
