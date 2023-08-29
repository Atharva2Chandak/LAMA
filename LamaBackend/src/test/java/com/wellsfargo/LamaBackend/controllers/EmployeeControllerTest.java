package com.wellsfargo.LamaBackend.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.wellsfargo.LamaBackend.dto.EmployeePostDto;
import com.wellsfargo.LamaBackend.entities.Employee;
import com.wellsfargo.LamaBackend.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
	
	@Mock
	private EmployeeServiceImpl mockEmpService;
	
	@InjectMocks
	EmployeeController underTest;
	
	@Test
	void createEmployee_shouldCreateSuccessfully() {
		// given
        EmployeePostDto expectedEmployee = new EmployeePostDto();
        expectedEmployee.setName("Test");
        expectedEmployee.setGender('o');
        when(mockEmpService.createEmployee(any())).thenReturn(expectedEmployee);

        // when
        Employee employee = new Employee();
        employee.setName("Test");
        employee.setGender('o');
        ResponseEntity<EmployeePostDto> response = underTest.createEmployee(employee);
        EmployeePostDto actual = response.getBody();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertEquals(expectedEmployee, actual),
                () -> assertEquals(expectedEmployee.getName(), actual.getName())
        );
	}
	
	@Test
	void createEmployee_shouldReturnBadRequest() {
		//given
		when(mockEmpService.createEmployee(any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required request body is missing"));
		
		//when
		Employee employee = new Employee();
		ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> mockEmpService.createEmployee(employee));
	    String expectedMessage = "Required request body is missing";
	    String actualMessage = exception.getMessage();
	    
	    //then
	    Assertions.assertTrue(actualMessage.contains(expectedMessage));
	    Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());	
	}
	
	@Test
	void updateEmployee_shouldUpdateSuccessfully() {
		//given
		EmployeePostDto newEmp = new EmployeePostDto();
		String id = UUID.randomUUID().toString().substring(7);
		newEmp.setId(id);
		newEmp.setName("Tester");
		newEmp.setGender('m');
		when(mockEmpService.patchEmployee(any(), any())).thenReturn(newEmp);

		//when
        Map<String, String> updateRequest = new HashMap<>();
        updateRequest.put("gender", "m");
        ResponseEntity<EmployeePostDto> response = underTest.updateEmployeeDetails(id, updateRequest);
		
        //then
        assertAll(
        		()-> assertNotNull(response.getBody()),
        		()-> assertEquals(HttpStatus.ACCEPTED, response.getStatusCode()),
        		()-> assertEquals(newEmp, response.getBody())
        );
	}
}
