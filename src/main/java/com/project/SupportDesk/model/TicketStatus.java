package com.project.SupportDesk.model;

import java.util.EnumSet;

public enum TicketStatus {
	OPEN,
	IN_PROGRESS,
	PENDING,
	RESOLVED,
	CLOSED;
	
	private EnumSet<TicketStatus> allowedTransitions;
	
	static {
		OPEN.allowedTransitions = EnumSet.of(IN_PROGRESS);
		IN_PROGRESS.allowedTransitions = EnumSet.of(PENDING,RESOLVED);
		PENDING.allowedTransitions = EnumSet.of(IN_PROGRESS);
		RESOLVED.allowedTransitions = EnumSet.of(CLOSED);
		CLOSED.allowedTransitions = EnumSet.noneOf(TicketStatus.class);
	}
	
	public boolean isValidTransition(TicketStatus newStatus) {
		boolean exists = allowedTransitions.contains(newStatus);
		return exists;
	}
}
