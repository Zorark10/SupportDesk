package com.project.SupportDesk.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

//DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
	private Integer id;
	private String subject;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private TicketStatus status;
	private TicketPriority priority;
	
	UserResponse user;
	List<CommentResponse> comments;
}
