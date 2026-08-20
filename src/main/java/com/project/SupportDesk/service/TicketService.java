package com.project.SupportDesk.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SupportDesk.exception.InvalidStatusTransitionException;
import com.project.SupportDesk.exception.TicketNotFoundException;
import com.project.SupportDesk.exception.UserNotFoundException;
import com.project.SupportDesk.model.Comment;
import com.project.SupportDesk.model.CommentResponse;
import com.project.SupportDesk.model.CreateTicketRequest;
import com.project.SupportDesk.model.Ticket;
import com.project.SupportDesk.model.TicketPriority;
import com.project.SupportDesk.model.TicketResponse;
import com.project.SupportDesk.model.TicketStatus;
import com.project.SupportDesk.model.User;
import com.project.SupportDesk.model.UserResponse;
import com.project.SupportDesk.repository.TicketRepo;
import com.project.SupportDesk.repository.UserRepo;

@Service
public class TicketService {
	@Autowired
	private TicketRepo repo;
	
	@Autowired
	private UserRepo userRepo;
	
	private TicketResponse toTicketResponse(Ticket tix) {
		UserResponse ur = UserResponse.builder().id(tix.getUser().getId()).name(tix.getUser().getName())
				.email(tix.getUser().getEmail()).build();
		List<Comment> comments = tix.getComments(); 
		List<CommentResponse> cmr = tix.getComments().stream()
			.map(comment -> CommentResponse.builder().id(comment.getId())
			.content(comment.getContent()).createdAt(comment.getCreatedAt()).build())
			.toList();
		TicketResponse tr = TicketResponse.builder().user(ur).comments(cmr).id(tix.getId()).subject(tix.getSubject()).description(tix.getDescription())
	.createdAt(tix.getCreateAt()).updatedAt(tix.getUpdatedAt()).status(tix.getStatus())
	.priority(tix.getPriority()).build();
		return tr;
	}
	
//	public Ticket addTicket(Ticket tix) {
//		return repo.save(tix);
//	}
	public Ticket findTicketEntityById(Integer id) {
		Ticket tix = repo.findById(id).orElse(null);
		if(tix == null) {
			throw new TicketNotFoundException("Ticket not found");
		}
		return tix;
	}
	public TicketResponse findTicketById(Integer id) {
		Ticket tix = repo.findById(id).orElse(null);
		if(tix == null) {
			throw new TicketNotFoundException("Ticket not found");
		}
			
		return toTicketResponse(tix);
	}
	
	public List<TicketResponse> getAllTickets(){
		List<Ticket> tickets = repo.findAll();
		List <TicketResponse> tix = tickets.stream().map(ticket -> toTicketResponse(ticket)).toList();
		return tix;
	}
	
	public void deleteTicket(Integer id) {
		repo.deleteById(id);
	}
	
	public TicketResponse updateTicket(Integer id, String subject, String description,
			TicketStatus status, TicketPriority priority) {
		
		Ticket currentTicket = findTicketEntityById(id);
		currentTicket.setSubject(subject);
		currentTicket.setDescription(description);
		currentTicket.setStatus(status);
		currentTicket.setPriority(priority);
		currentTicket.setUpdatedAt(LocalDateTime.now());
		Ticket saved = repo.save(currentTicket);
		return toTicketResponse(saved);
	}
	
	public TicketResponse createTicket(CreateTicketRequest request) {
		Optional<User> opUser = userRepo.findById(request.getUserId());
		User user = opUser.orElseThrow(() -> new UserNotFoundException("User Not Found"));
		Ticket tix = Ticket.builder().subject(request.getSubject())
					.description(request.getDescription()).status(request.getStatus())
					.priority(request.getPriority()).user(user).createAt(LocalDateTime.now()).build();
		Ticket savedTicket = repo.save(tix);
		return toTicketResponse(savedTicket);
	}
	
	public TicketResponse updateTicketStatus(Integer ticketId, TicketStatus newStatus){
		Ticket ticket = findTicketEntityById(ticketId);
		TicketStatus currentTicketStatus = ticket.getStatus();
		if(currentTicketStatus.isValidTransition(newStatus)) {
			ticket.setStatus(newStatus);
		} else {
			throw new InvalidStatusTransitionException("Invalid Transition");
		}
		Ticket saved = repo.save(ticket);
		return toTicketResponse(saved);
	}
}
