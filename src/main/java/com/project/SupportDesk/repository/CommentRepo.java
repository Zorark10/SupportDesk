package com.project.SupportDesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.SupportDesk.model.Comment;
public interface CommentRepo extends JpaRepository<Comment, Integer>{
	public List<Comment> findByTicketId(Integer id);
}
