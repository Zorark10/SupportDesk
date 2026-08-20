package com.project.SupportDesk.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//DTO

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketRequest {
	private String subject, description;
	private TicketStatus status;
	private TicketPriority priority;
	private Integer userId;
}
