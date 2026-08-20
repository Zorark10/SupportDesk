package com.project.SupportDesk.service;

import com.project.SupportDesk.exception.CommentNotFoundException;
import com.project.SupportDesk.model.Comment;
import com.project.SupportDesk.model.CreateCommentRequest;
import com.project.SupportDesk.model.Ticket;
import com.project.SupportDesk.model.TicketResponse;
import com.project.SupportDesk.model.UpdateCommentRequest;
import com.project.SupportDesk.model.User;
import com.project.SupportDesk.repository.CommentRepo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
	@Autowired
	private CommentRepo repo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TicketService tixService;
	
	public Comment createComment(CreateCommentRequest request, Integer tixid)  {
		User user = userService.findById(request.getUserId());
		Ticket tix = tixService.findTicketEntityById(tixid);
		
		Comment cm = Comment.builder().user(user).ticket(tix).createdAt(LocalDateTime.now()).content(request.getContent())
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
		return cm;
	}
	
	public void deleteComment(Integer id) {
		repo.deleteById(id);
	}

	public Comment updateComment(Integer id, UpdateCommentRequest request){
		Comment cm = findCommentById(id);
		cm.setContent(request.getContent());
		return repo.save(cm);
	}
	
	public List<Comment> getCommentsByTixId(Integer id){
		
		Ticket tix = tixService.findTicketEntityById(id);
		List<Comment> cm = repo.findByTicketId(id);
		return cm;
		
	}
}
