package com.emp.statemachine;

import java.util.Optional;

public enum EmployeeState {

	ADDED,
	IN_CHECK,
	APPROVED,
	ACTIVE;
	
	public Optional<EmployeeState> next() {
	    switch (this) {
	      case ADDED: return Optional.of(IN_CHECK);
	      case IN_CHECK: return Optional.of(APPROVED);
	      case APPROVED: return Optional.of(ACTIVE);
	      default: return Optional.of(ADDED);
	  }
	}
}
