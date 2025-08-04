package org.lessons.java.spring.ticket_platform.controller;

import org.lessons.java.spring.ticket_platform.model.Note;
import org.lessons.java.spring.ticket_platform.model.Ticket;
import org.lessons.java.spring.ticket_platform.repository.TicketRepository;
import org.lessons.java.spring.ticket_platform.repository.UserRepository;
import org.lessons.java.spring.ticket_platform.repository.CategoryRepository;
import org.lessons.java.spring.ticket_platform.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final CategoryRepository categoryRepository;

    TicketController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping({ "", "/" })
    public String index(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Ticket> tickets;

        if (search != null && !search.isEmpty()) {
            tickets = ticketRepository.findByTitleContainingIgnoreCase(search);
        } else {
            tickets = ticketRepository.findAll();
        }

        model.addAttribute("tickets", tickets);
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("search", search); 

        return "tickets/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
        List<Note> notes = noteRepository.findByTicket(ticket);
        model.addAttribute("ticket", ticket);
        model.addAttribute("notes", notes);
        model.addAttribute("newNote", new Note());
        return "tickets/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("operators", userRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "tickets/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("operators", userRepository.findAll());
            model.addAttribute("categories", categoryRepository.findAll());

            return "/tickets/create";
        }

        ticketRepository.save(formTicket);

        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
        model.addAttribute("ticket", ticket);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("operators", userRepository.findAll());
        return "tickets/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("ticket") Ticket formTicket,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("operators", userRepository.findAll());
            return "/ticket/edit";
        }
        
        ticketRepository.save(formTicket);

        return "redirect:/tickets";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "tickets/edit";
        }

        ticketRepository.save(formTicket);

        return "redirect:/tickets";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {

        Ticket ticketToDelete = ticketRepository.findById(id).get();

        ticketRepository.delete(ticketToDelete);

        return "redirect:/tickets";
    }

}
