package com.emp.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import com.emp.dto.EmployeeDto;
import com.emp.entity.EmployeeDO;
import com.emp.exception.ConstraintViolationException;
import com.emp.exception.IllegalStateMachineException;
import com.emp.repo.EmployeeRepository;
import com.emp.statemachine.EmployeeEvent;
import com.emp.statemachine.EmployeeState;

@Service
public class EmployeeService {
	
	Log logger = LogFactory.getLog(EmployeeService.class);

	@Autowired
	private EmployeeRepository empRep;
	
	@Autowired 
	private StateMachine<EmployeeState, EmployeeEvent> stateMachine;
	
	public void addEmployee(EmployeeDto empDto) throws ConstraintViolationException {
		if(empRep.findByEmail(empDto.getEmail())!=null)
			throw new ConstraintViolationException("Employee with this emailId already exists");
		EmployeeDO empDo = new EmployeeDO();
		empDto.setEmployeeState(stateMachine.getInitialState().getId());
		BeanUtils.copyProperties(empDto, empDo);
		empRep.save(empDo);
	}
	
	public List<EmployeeDO> findAll() {
		return empRep.findAll();
	}
	
	public EmployeeDto validateAndUpdateEmployee(String email, String newState) throws IllegalStateMachineException {
		logger.info("Email:"+email+" new State:"+newState);
		EmployeeDO empDo = empRep.findByEmail(email);
		if(empDo!=null) {
			boolean isApproved = stateMachine.getTransitions().stream()
			.anyMatch(t->t.getSource().getId().equals(empDo.getEmployeeState()) 
					&& t.getTarget().getId().equals(EmployeeState.valueOf(newState)));
			logger.info("Calling Update Employee state");
			updateEmployeeState(isApproved, empDo, newState);		
		}else {
			throw new EntityNotFoundException("Email address is not registered");
		}
		EmployeeDto empDto = new EmployeeDto();
		BeanUtils.copyProperties(empDo, empDto);
		return empDto;
	}
	
	public void updateEmployeeState(boolean isApproved, EmployeeDO empDo, String newState) throws IllegalStateMachineException {
		try {   
		if(isApproved) {
			empDo.setEmployeeState(EmployeeState.valueOf(newState));
			empRep.save(empDo);
		}else {
			throw new IllegalStateMachineException("Transition to this state is not allowed");
		}
	}catch(IllegalArgumentException ex){
		throw new IllegalArgumentException("State machine is invalid");
	}
	}
}
