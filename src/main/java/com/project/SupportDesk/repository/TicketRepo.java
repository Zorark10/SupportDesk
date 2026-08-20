package com.project.SupportDesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.SupportDesk.model.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {

}
