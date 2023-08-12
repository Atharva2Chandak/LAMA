package com.wellsfargo.LamaBackend.service;

import java.util.List;

import com.wellsfargo.LamaBackend.dto.EmployeeGetDto;
import com.wellsfargo.LamaBackend.entities.Employee;

public interface EmployeeService {
	Employee createEmployee(Employee employee);
	EmployeeGetDto getEmployee(String id);
	List<EmployeeGetDto> getAllEmployees();
}
