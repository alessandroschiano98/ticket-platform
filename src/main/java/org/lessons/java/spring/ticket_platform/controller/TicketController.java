package org.lessons.java.spring.ticket_platform.controller;

import org.lessons.java.spring.ticket_platform.model.Ticket;
import org.lessons.java.spring.ticket_platform.repository.TicketRepository;
import org.lessons.java.spring.ticket_platform.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final CategoryRepository categoryRepository;

    @Autowired
    private TicketRepository ticketRepository;

    TicketController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping({ "", "/" })
    public String index(Model model) {
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        model.addAttribute("ticket", new Ticket());  // Se hai form nella pagina index
        return "tickets/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        model.addAttribute("ticket", ticket);
        return "tickets/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "tickets/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "/tickets/create";
        }

        ticketRepository.save(formTicket);

        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        model.addAttribute("ticket", ticket);
        return "tickets/edit";
    }
}
