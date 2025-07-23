package org.lessons.java.spring.ticket_platform.controller;

import java.util.List;

import org.lessons.java.spring.ticket_platform.model.Ticket;
import org.lessons.java.spring.ticket_platform.model.User;
import org.lessons.java.spring.ticket_platform.repository.TicketRepository;
import org.lessons.java.spring.ticket_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/operators")
public class OperatorController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping({ "", "/" })
    public String index(Model model) {
        List<User> operators = userRepository.findByRolesNameIn(List.of("OPERATOR"));
        model.addAttribute("operators", operators);
        return "operators/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        User operator = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid operator Id:" + id));

        List<Ticket> assignedTickets = ticketRepository.findByOperatorId(id);
        boolean isAvailable = assignedTickets.isEmpty();


        model.addAttribute("operator", operator);
        model.addAttribute("assignedTickets", assignedTickets);
        model.addAttribute("isAvailable", isAvailable);

        return "operators/show";
    }

}
