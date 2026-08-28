package com.project.SupportDesk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.SupportDesk.model.Comment;
import com.project.SupportDesk.model.CreateCommentRequest;
import com.project.SupportDesk.model.UpdateCommentRequest;
import com.project.SupportDesk.service.CommentService;

@RestController
public class CommentController {
	
	@Autowired
	private CommentService service;
	
	@PostMapping("/ticket/{ticketId}/comments")
	public Comment createComment(@PathVariable Integer ticketId, @RequestBody CreateCommentRequest request) throws Exception {
		return service.createComment(request, ticketId);		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/comments")
	public List<Comment> getAll(){
		return service.getAllComments();
	}
	
	@GetMapping("/comment/{commentId}")
	public Comment findCommentById(@PathVariable Integer commentId){
		return service.findCommentById(commentId);
	}
	
	@DeleteMapping("/comment/{commentId}/delete")
	public void deleteComment(@PathVariable Integer commentId) {
		System.out.println("DELETE COMMENT CONTROLLER REACHED");
		service.deleteComment(commentId);
	}
	
	@PatchMapping("/comment/{commentId}/update")
	public Comment updateComment(@PathVariable Integer commentId, @RequestBody UpdateCommentRequest request){
		return service.updateComment(commentId, request);
	}
	
	@GetMapping("/ticket/{ticketId}/comments")
	public List<Comment> getCommentByTixId(@PathVariable Integer ticketId){
		return service.getCommentsByTixId(ticketId);
	}
	
	
}
