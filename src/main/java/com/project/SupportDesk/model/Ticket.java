package com.project.SupportDesk.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import jakarta.persistence.CascadeType;
//enum TicketStatus {
//	OPEN,
//	IN_PROGRESS,
//	PENDING,
//	RESOLVED,
//	CLOSED
//}

//enum TicketPriority {
//	LOW, MEDIUM, HIGH, URGENT
//}

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String subject, description;
	
	@JsonFormat(pattern = "dd MM yyyy, hh:mm: a")
	private LocalDateTime createAt, updatedAt;
	
	@Enumerated(EnumType.STRING)
	private TicketStatus status;
	@Enumerated(EnumType.STRING)
	private TicketPriority priority;
	
	@ManyToOne
	@JsonBackReference("user-tickets")
	private User user;
	
	@Builder.Default
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("ticket-comments")
	private List<Comment> comments = new ArrayList<>();
}
