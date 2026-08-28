package com.project.SupportDesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.SupportDesk.model.Ticket;
import com.project.SupportDesk.model.User;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {
	public List<Ticket> findByUser(User user);
}
