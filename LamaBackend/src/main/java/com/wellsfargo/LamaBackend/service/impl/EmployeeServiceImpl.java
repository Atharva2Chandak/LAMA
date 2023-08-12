package com.wellsfargo.LamaBackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.LamaBackend.dto.EmployeeGetDto;
import com.wellsfargo.LamaBackend.dto.EmployeePostDto;
import com.wellsfargo.LamaBackend.entities.Employee;
import com.wellsfargo.LamaBackend.jpaRepos.EmployeeRepository;
import com.wellsfargo.LamaBackend.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public EmployeePostDto createEmployee(Employee employee) {
		Employee savedEmployee = employeeRepository.save(employee);
		return this.modelMapper.map(savedEmployee, EmployeePostDto.class);
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
	
	public EmployeePostDto patchEmployee(String id, Map<String ,String> employee) throws Exception {
		
		Optional<Employee> dbEmp = this.employeeRepository.findById(id);
		if(dbEmp.isPresent()) {
			Employee foundEmployee = dbEmp.get();
			
			for(String key: employee.keySet()) {
				
				String updateValue = employee.get(key);
				
				//To-do validate updateValue
				if(key.equalsIgnoreCase("designation")) {
					foundEmployee.setDesignation(updateValue);
				}
				
				if(key.equalsIgnoreCase("department")) {
					foundEmployee.setDepartment(updateValue);
				}
				
				if(key.equalsIgnoreCase("dob")) {
					foundEmployee.setDob(updateValue);
				}
				
				if(key.equalsIgnoreCase("doj")) {
					foundEmployee.setDoj(updateValue);
				}
				
				if(key.equalsIgnoreCase("gender")) {
					foundEmployee.setGender(updateValue.charAt(0));
				}				
			}
			
			Employee updatedEmployee = this.employeeRepository.save(foundEmployee);
			return this.modelMapper.map(updatedEmployee, EmployeePostDto.class);
		} else {
			throw new Exception("Employee not found");
		}
	}
}
