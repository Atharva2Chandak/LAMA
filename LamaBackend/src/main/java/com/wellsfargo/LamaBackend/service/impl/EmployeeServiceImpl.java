package com.wellsfargo.LamaBackend.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wellsfargo.LamaBackend.dto.EmployeeGetDto;
import com.wellsfargo.LamaBackend.dto.EmployeePostDto;
import com.wellsfargo.LamaBackend.entities.ERole;
import com.wellsfargo.LamaBackend.entities.Employee;
import com.wellsfargo.LamaBackend.entities.Role;
import com.wellsfargo.LamaBackend.entities.User;
import com.wellsfargo.LamaBackend.jpaRepos.EmployeeRepository;
import com.wellsfargo.LamaBackend.jpaRepos.RoleRepository;
import com.wellsfargo.LamaBackend.jpaRepos.UserRepository;
import com.wellsfargo.LamaBackend.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public EmployeePostDto createEmployee(Employee employee) {
		
		//Encoding the password
		employee.setPassword(encoder.encode(employee.getPassword()));
		
		//Setting default to create employee
		employee.setIsAdmin('0');
		
		//Saving the employee
		Employee savedEmployee = employeeRepository.save(employee);
		
		//Duplicating the employee in the user table required by spring security
		User springSecurityUser = new User(savedEmployee.getId(), savedEmployee.getPassword());
		
		//Creating user role
		Set<Role> roles = new HashSet<>();
		Role userRole = this.roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		
		//Setting role
		springSecurityUser.setRoles(roles);
		
		//Saving the user
		this.userRepository.save(springSecurityUser);
		
		return this.modelMapper.map(savedEmployee, EmployeePostDto.class);
	}
	
	public EmployeeGetDto getEmployee(String id) throws ResponseStatusException {
		Optional<Employee> employee = this.employeeRepository.findById(id);
		if(employee.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested Employee not found");
		EmployeeGetDto employeeGetDto = this.modelMapper.map(employee.get(), EmployeeGetDto.class);
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
	
	public EmployeePostDto patchEmployee(String id, Map<String ,String> employee) throws ResponseStatusException {
		
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
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Requested Employee not found");
		}
	}
	
	public Boolean deleteEmployee(String id) {
		if(id == null) return false;
			this.employeeRepository.deleteById(id);
		return true;
	}
	
	public Employee getEmployeeEntity(String id) throws ResponseStatusException {
		Optional<Employee> employee = this.employeeRepository.findById(id);
		if(employee.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested Employee not found");
		return employee.get();
	}
}
