package com.project.SupportDesk.service;

import com.project.SupportDesk.exception.CommentNotFoundException;
import com.project.SupportDesk.model.Comment;
import com.project.SupportDesk.model.CreateCommentRequest;
import com.project.SupportDesk.model.Role;
import com.project.SupportDesk.model.Ticket;
import com.project.SupportDesk.model.UpdateCommentRequest;
import com.project.SupportDesk.model.User;
import com.project.SupportDesk.repository.CommentRepo;
import com.project.SupportDesk.repository.UserRepo;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
	@Autowired
	private CommentRepo repo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TicketService tixService;
	
	public Comment createComment(CreateCommentRequest request, Integer tixid)  {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User currUser = userRepo.findByEmail(email);
		Ticket tix = tixService.findTicketEntityById(tixid);
		
		if(currUser.getRole() != Role.ADMIN && !tix.getUser().getId().equals(currUser.getId())){
			throw new AccessDeniedException("You cannot comment on this ticket!");
		}
		
		Comment cm = Comment.builder().user(currUser).ticket(tix).createdAt(LocalDateTime.now())
					.content(request.getContent())
					.build();
		return repo.save(cm);
	}
	
	public List<Comment> getAllComments(){
		return repo.findAll();
	}
	
	public Comment findCommentById(Integer id) throws CommentNotFoundException{
		Comment cm = repo.findById(id).orElse(null);
		if(cm == null) {
			throw new CommentNotFoundException("Comment not found");
		}
		Ticket tix = cm.getTicket();
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User currUser = userRepo.findByEmail(email);
		if(currUser.getRole() != Role.ADMIN && !tix.getUser().getId().equals(currUser.getId())){
			throw new AccessDeniedException("You cannot access this comment!");
		}
		return cm;
	}
	
	public void deleteComment(Integer id) {
		Comment cm = findCommentById(id);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User currUser = userRepo.findByEmail(email);
		
		if(currUser.getRole() != Role.ADMIN && !cm.getUser().getId().equals(currUser.getId())){
			throw new AccessDeniedException("You cannot delete this comment!");
		}
		repo.deleteById(id);
	}

	public Comment updateComment(Integer id, UpdateCommentRequest request){
		Comment cm = findCommentById(id);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User currUser = userRepo.findByEmail(email);
		if(currUser.getRole() != Role.ADMIN && !cm.getUser().getId().equals(currUser.getId())){
			throw new AccessDeniedException("You cannot access this ticket!");
		}
		cm.setContent(request.getContent());
		return repo.save(cm);
	}
	
	public List<Comment> getCommentsByTixId(Integer id){
		
		Ticket tix = tixService.findTicketEntityById(id);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User currUser = userRepo.findByEmail(email);
		
		if(currUser.getRole() != Role.ADMIN && !tix.getUser().getId().equals(currUser.getId())){
			throw new AccessDeniedException("You cannot access this ticket!");
		}
		List<Comment> cm = repo.findByTicketId(id);
		return cm;
		
	}
}
