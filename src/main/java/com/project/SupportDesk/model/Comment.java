package com.project.SupportDesk.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String content;
	
	@JsonFormat(pattern = "dd MM yyyy, hh:mm: a")
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JsonBackReference("ticket-comments")
	private Ticket ticket;
	
	@ManyToOne
	@JsonBackReference("user-comments")
	private User user;
	
	
	
	
}
