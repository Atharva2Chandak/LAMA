package com.wellsfargo.LamaBackend.service;

import java.util.List;
import java.util.Map;

import com.wellsfargo.LamaBackend.dto.EmployeeGetDto;
import com.wellsfargo.LamaBackend.dto.EmployeePostDto;
import com.wellsfargo.LamaBackend.entities.Employee;

public interface EmployeeService {
	EmployeePostDto createEmployee(Employee employee);
	EmployeeGetDto getEmployee(String id);
	List<EmployeeGetDto> getAllEmployees();
	EmployeePostDto patchEmployee(String id, Map<String, String> employee) throws Exception;
}
