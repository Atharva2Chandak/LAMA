package com.wellsfargo.LamaBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wellsfargo.LamaBackend.dto.EmployeePostDto;
import com.wellsfargo.LamaBackend.entities.ERole;
import com.wellsfargo.LamaBackend.entities.Employee;
import com.wellsfargo.LamaBackend.entities.Role;
import com.wellsfargo.LamaBackend.jpaRepos.EmployeeRepository;
import com.wellsfargo.LamaBackend.jpaRepos.RoleRepository;
import com.wellsfargo.LamaBackend.jpaRepos.UserRepository;
import com.wellsfargo.LamaBackend.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	
	@Mock
	private EmployeeRepository mockEmpRepo;
	
	@Mock
	private ModelMapper mockModelMapper;
	
	@Mock 
	private RoleRepository mockRoleRepository;
	
	@Mock
	private UserRepository mockUserRepository;
	
	@Mock
	private PasswordEncoder mockEncoder;
	
	@InjectMocks
	private EmployeeServiceImpl underTest;
	
	@Test
	void createEmployee_shouldCreateSuccessfully() {
		//given
		Employee employee = new Employee();
		employee.setName("test");
		
		
		EmployeePostDto empPostDto = new EmployeePostDto();
		empPostDto.setName("test");
		
		Role userRole = new Role();
		userRole.setName(ERole.ROLE_USER);
		
		when(mockEncoder.encode(any())).thenReturn("Mocked password hash");
		when(mockEmpRepo.save(any())).thenReturn(employee);
		when(mockRoleRepository.findByName(any())).thenReturn(Optional.of(userRole));
		when(mockUserRepository.save(any())).thenReturn(null);
		when(mockModelMapper.map(any(), any())).thenReturn(empPostDto);
		
		
		//when
		EmployeePostDto createdEmp = underTest.createEmployee(employee);
		
		
		//then
		assertEquals(empPostDto.getName(), createdEmp.getName());
	}
}
