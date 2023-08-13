package com.wellsfargo.LamaBackend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wellsfargo.LamaBackend.dto.EmployeeGetDto;
import com.wellsfargo.LamaBackend.dto.EmployeePostDto;
import com.wellsfargo.LamaBackend.entities.Employee;
import com.wellsfargo.LamaBackend.service.impl.EmployeeServiceImpl;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeServiceImpl employeeServiceImpl;
	
	@PostMapping("/create")
	public ResponseEntity<EmployeePostDto> createEmployee(@RequestBody Employee employee) {
		EmployeePostDto createdEmployee = this.employeeServiceImpl.createEmployee(employee);
		return new ResponseEntity<EmployeePostDto>(createdEmployee, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeGetDto> getEmployee(@PathVariable String id) throws ResponseStatusException {
		EmployeeGetDto foundEmployee = this.employeeServiceImpl.getEmployee(id);
		return new ResponseEntity<EmployeeGetDto>(foundEmployee, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<EmployeeGetDto>> getAllEmployees() {
		List<EmployeeGetDto> employees = employeeServiceImpl.getAllEmployees();
		return new ResponseEntity<List<EmployeeGetDto>>(employees, HttpStatus.OK);
	}
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<EmployeePostDto> updateEmployeeDetails(@PathVariable String id, @RequestBody Map<String,String> employee) throws ResponseStatusException{
		try {
			EmployeePostDto updatedEmployee = this.employeeServiceImpl.patchEmployee(id, employee);
			return new ResponseEntity<EmployeePostDto>(updatedEmployee, HttpStatus.ACCEPTED);
		} catch (ResponseStatusException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteEmployee(@PathVariable String id) throws ResponseStatusException {
		boolean deleteStatus = this.employeeServiceImpl.deleteEmployee(id);
		if(deleteStatus)
			return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
}
