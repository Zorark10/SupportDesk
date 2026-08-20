package com.project.SupportDesk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.SupportDesk.model.CreateTicketRequest;
import com.project.SupportDesk.model.Ticket;
import com.project.SupportDesk.model.TicketPriority;
import com.project.SupportDesk.model.TicketResponse;
import com.project.SupportDesk.model.TicketStatus;
import com.project.SupportDesk.model.User;
import com.project.SupportDesk.service.TicketService;

@RestController
public class TicketController {
	@Autowired
	private TicketService service;
	
	@GetMapping("/ticket/{id}")
	public TicketResponse findById(@PathVariable Integer id) throws Exception {
		return service.findTicketById(id);
	}
	
	@GetMapping("/ticket")
	public List<TicketResponse> getAllTickets(){
		return service.getAllTickets();
	}
	
	@DeleteMapping("/ticket/delete/{id}")
	public void deleteTickets(@PathVariable Integer id) {
		service.deleteTicket(id);
	}
	
	@PatchMapping("/ticket/update/{id}")
	public TicketResponse updateTicket(@PathVariable Integer id, @RequestParam String subject, @RequestParam String description,
			@RequestParam TicketStatus status, @RequestParam TicketPriority priority) {
		return service.updateTicket(id, subject, description, status, priority);
	}
	
	@PostMapping("/ticket")
	public TicketResponse createTicket(@RequestBody CreateTicketRequest request) {
		return service.createTicket(request);
	}
	
	@PatchMapping("/ticket/{ticketId}/status")
	public TicketResponse updateTicketStatus(@PathVariable Integer ticketId, @RequestBody TicketStatus newStatus) {
		return service.updateTicketStatus(ticketId, newStatus);
	}
}
