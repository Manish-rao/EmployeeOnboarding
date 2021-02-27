package com.emp.dto;

import javax.validation.constraints.Email;

import com.emp.statemachine.EmployeeState;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.Hidden;

@JsonInclude
public class EmployeeDto {
	
	@Email
	private String email;
	private String userName;
	private long phoneNumber;
	private String address;
	private int age;
	@Hidden
	private EmployeeState employeeState;
	
	public EmployeeDto(String email, String userName, long phoneNumber, String address, int age) {
		this.email = email;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.age = age;
	}
	
	public EmployeeDto() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public EmployeeState getEmployeeState() {
		return employeeState;
	}

	public void setEmployeeState(EmployeeState added) {
		this.employeeState = added;
	}
	
	
}
