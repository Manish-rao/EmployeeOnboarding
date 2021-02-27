package com.emp.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.emp.dto.EmployeeDto;
import com.emp.entity.EmployeeDO;
import com.emp.repo.EmployeeRepository;
import com.emp.service.EmployeeService;
import com.emp.statemachine.EmployeeState;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeStatesTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Test
	public void addEmployeeTest() throws Exception {
		EmployeeDto empDto = new EmployeeDto("mytest@123.com", "Test user", 87323242L,
				"test Address", 30);
		mockMvc.perform(post("/addEmployee").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(empDto))).andExpect(status().isOk());
	}
	
	@Test
	public void addEmployeeTest_withDuplicateEmail() throws Exception {
		EmployeeDO empDoNew = new EmployeeDO("mytest12@123.com", "Test user", 87323242L,
				"test Address", 30, EmployeeState.ADDED);
		empRepo.save(empDoNew);
		EmployeeDto empDto = new EmployeeDto("mytest12@123.com", "Test user", 87323242L,
				"test Address", 30);
		mockMvc.perform(post("/addEmployee").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(empDto))).andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateEmployee_withStatusOk() throws Exception {
		EmployeeDO empDoNew = new EmployeeDO("mytest3@123.com", "Test user", 87323242L,
					"test Address", 30, EmployeeState.ADDED);
		empRepo.save(empDoNew);
		EmployeeDto empDto = new EmployeeDto();
		BeanUtils.copyProperties(empDoNew, empDto);
		mockMvc.perform(post("/updateEmployee")
				.param("newState", "IN-CHECK")
				.param("email",empDto.getEmail())
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(empDto))).andExpect(status().isOk());
	}
	
	@Test
	public void updateEmployee_withIllegalState() throws Exception {
		EmployeeDO empDoNew = new EmployeeDO("mytest4@123.com", "Test user", 87323242L,
					"test Address", 30, EmployeeState.ADDED);
		empRepo.save(empDoNew);
		EmployeeDto empDto = new EmployeeDto();
		BeanUtils.copyProperties(empDoNew, empDto);
		mockMvc.perform(post("/updateEmployee")
				.param("newState", empDto.getEmployeeState().toString())
				.param("email",empDto.getEmail())
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(empDto))).andExpect(status().isBadRequest());
	}
	
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
