package com.emp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.emp.statemachine.EmployeeState;

@Entity
@Table(name="EMPLOYEE")
public class EmployeeDO {

	@Id
	private String email;
	
	@Column
	private String userName;
	
	@Column
	private long phoneNumber;
	
	@Column
	private String address;
	
	@Column
	private int age;
	
	@Enumerated(EnumType.ORDINAL)
	private EmployeeState employeeState;
	
	public EmployeeDO(String email, String userName, long phoneNumber, String address, int age, EmployeeState employeeState) {
		this.email = email;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.age = age;
		this.employeeState = employeeState;
	}
	
	public EmployeeDO() {
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

	public void setEmployeeState(EmployeeState employeeState) {
		this.employeeState = employeeState;
	}
	
	
}
