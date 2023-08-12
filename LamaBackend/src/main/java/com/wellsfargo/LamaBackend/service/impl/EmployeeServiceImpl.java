package com.wellsfargo.LamaBackend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.LamaBackend.dto.EmployeeGetDto;
import com.wellsfargo.LamaBackend.entities.Employee;
import com.wellsfargo.LamaBackend.jpaRepos.EmployeeRepository;
import com.wellsfargo.LamaBackend.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	public EmployeeGetDto getEmployee(String id) {
		Employee employee = this.employeeRepository.findById(id)
							.orElse(new Employee());
		EmployeeGetDto employeeGetDto = this.modelMapper.map(employee, EmployeeGetDto.class);
		return employeeGetDto;
	}
	public List<EmployeeGetDto> getAllEmployees() {
		List<Employee> employees = this.employeeRepository.findAll();
		List<EmployeeGetDto> foundEmployeesDto = new ArrayList<>();
		for(var employee: employees) {
			foundEmployeesDto.add(this.modelMapper.map(employee, EmployeeGetDto.class));
		}
		return foundEmployeesDto;
	}
}
