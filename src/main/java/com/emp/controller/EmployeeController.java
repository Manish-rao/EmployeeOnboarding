package com.emp.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emp.dto.EmployeeDto;
import com.emp.exception.IllegalStateMachineException;
import com.emp.service.EmployeeService;
import com.emp.statemachine.EmployeeState;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	private static final String inCheck = "IN-CHECK";
	
	@Operation(summary = "Add new employee")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Added employee with Default state", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema (implementation = EmployeeDto.class)) }),
			  @ApiResponse(responseCode = "400", description = "Duplicate email address supplied", 
			    content = @Content)})
	@PostMapping("/addEmployee")
	public <T> ResponseEntity<T> addEmployee(@Valid @RequestBody EmployeeDto employeeDto){
		try {
			employeeService.addEmployee(employeeDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Operation(summary = "Update Employee with new state")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Employee successfully updated with new state", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema (implementation = EmployeeDto.class)) }),
			  @ApiResponse(responseCode = "400", description = "Email address or state does not exist or state is illegal", 
			    content = @Content)})
	@PostMapping("/updateEmployee")
	public EmployeeDto updateEmployeeState(@RequestParam String newState, @RequestParam String email) throws IllegalStateMachineException {
		if(EmployeeState.IN_CHECK.toString().equals(newState))
			throw new IllegalStateMachineException("IN_CHECK is an invalid state, please try with IN-CHECK");
		if(inCheck.equals(newState)) {
			newState = EmployeeState.IN_CHECK.toString();
		}
		return employeeService.validateAndUpdateEmployee(email, newState);
	}
	
}
