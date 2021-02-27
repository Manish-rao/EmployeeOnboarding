package com.emp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.entity.EmployeeDO;

public interface EmployeeRepository extends JpaRepository<EmployeeDO, String>{
	
	public EmployeeDO findByEmail(String email);
}
