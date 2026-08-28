package com.project.SupportDesk.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.SupportDesk.exception.InvalidStatusTransitionException;
import com.project.SupportDesk.exception.TicketNotFoundException;
import com.project.SupportDesk.exception.UserNotFoundException;
import com.project.SupportDesk.model.Comment;
import com.project.SupportDesk.model.CommentResponse;
import com.project.SupportDesk.model.CreateTicketRequest;
import com.project.SupportDesk.model.Role;
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
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User currUser = userRepo.findByEmail(email);
		
		if(currUser.getRole() != Role.ADMIN && !tix.getUser().getId().equals(currUser.getId())){
			throw new AccessDeniedException("You cannot access this ticket!");
		}
	
		return toTicketResponse(tix);
	}
	
	public List<TicketResponse> getAllTickets(){
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User currUser = userRepo.findByEmail(email);
		
		if(currUser.getRole() == Role.ADMIN) {
			List<Ticket> tickets = repo.findAll();
			List <TicketResponse> tix = tickets.stream().map(ticket -> toTicketResponse(ticket)).toList();
			return tix;
		} else {
			List<Ticket> tickets = repo.findByUser(currUser);
			List <TicketResponse> tix = tickets.stream().map(ticket -> toTicketResponse(ticket)).toList();
			return tix;
		}
		
//		List <TicketResponse> tix = tickets.stream().map(ticket -> toTicketResponse(ticket)).toList();
//		return tix;
	}
	
	public void deleteTicket(Integer id) {
		Ticket tix = findTicketEntityById(id);

	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
	    User currUser = userRepo.findByEmail(email);

	    if(currUser.getRole() != Role.ADMIN && !tix.getUser().getId().equals(currUser.getId())) {
	        throw new AccessDeniedException("You cannot delete this ticket!");
	    }
		repo.deleteById(id);
	}
	
	public TicketResponse updateTicket(Integer id, String subject, String description,
			TicketStatus status, TicketPriority priority) {
		
		Ticket currentTicket = findTicketEntityById(id);
		currentTicket.setSubject(subject);
		currentTicket.setDescription(description);
		TicketStatus currentTicketStatus = currentTicket.getStatus();
		if(currentTicketStatus.isValidTransition(status)) {
			currentTicket.setStatus(status);
		} else {
			throw new InvalidStatusTransitionException("Invalid Transition");
		}

		currentTicket.setPriority(priority);
		currentTicket.setUpdatedAt(LocalDateTime.now());
		Ticket saved = repo.save(currentTicket);
		return toTicketResponse(saved);
	}
	
	public TicketResponse createTicket(CreateTicketRequest request) {
		String email = SecurityContextHolder.getContext()
						.getAuthentication().getName();
		User user = userRepo.findByEmail(email);
		
		if(user == null)
			throw new UserNotFoundException("User Not Found!");
		
		Ticket tix = Ticket.builder().subject(request.getSubject())
					.description(request.getDescription()).status(TicketStatus.OPEN)
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
