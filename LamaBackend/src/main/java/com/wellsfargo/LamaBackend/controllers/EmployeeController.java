package com.wellsfargo.LamaBackend.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.wellsfargo.LamaBackend.entities.EmployeeCardDetail;
import com.wellsfargo.LamaBackend.entities.Item;
import com.wellsfargo.LamaBackend.entities.LoanCard;
import com.wellsfargo.LamaBackend.service.impl.EmployeeServiceImpl;
import com.wellsfargo.LamaBackend.service.impl.ItemServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeServiceImpl employeeServiceImpl;
	
	@Autowired
	private ItemServiceImpl itemServiceImpl;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<EmployeePostDto> createEmployee(@Valid @RequestBody Employee employee) {
		if(!(employee.getGender() == 'm' || employee.getGender()=='f' || employee.getGender()=='o')) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Gender can either be m,f or o");
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
	
	//At later stages the empId comes from the jwt
	@PostMapping("/loanItem/{empId}")
	public ResponseEntity<Map<String,String>> loanAnItem(@PathVariable String empId, @RequestBody Map<String, String> requestBody) throws ResponseStatusException {
		if(requestBody.size() == 0 || !requestBody.containsKey("itemId")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		/*
		 * Searching for the item in the database
		 * ItemService handles if the item was not found
		 */
		Item foundItem = this.itemServiceImpl.getItem(requestBody.get("itemId"));
		
		/*
		 * Searching for the respective Employee in the database
		 * Employee service handles if the employee was not found
		 */
		Employee foundEmployee = this.employeeServiceImpl.getEmployeeEntity(empId);
		
		//Issuing the foundItem to foundEmployee
		if(!this.itemServiceImpl.issueItemToEmployee(foundItem, foundEmployee)) throw new ResponseStatusException(HttpStatus.CONFLICT, "Item is already issued");
		
		//Saving the updated found item
		//To-do : (optimization) createItem checks for the loan card again, not required in this use case
		Item updatedItem = this.itemServiceImpl.createItem(foundItem);
		
		
		/*
		 * Get loan card matching the item category : Already stored as foreign key in item
		 * Create employee to loan card mapping (employee_card_details) save this loan card in employee EmployeeCardDetail list
		 */
		LoanCard loanCard = updatedItem.getLoanCard();
		List<EmployeeCardDetail> empLoanList = foundEmployee.getEmployeeCardDetails();
		
		EmployeeCardDetail empCardDetail = new EmployeeCardDetail(foundEmployee,loanCard, new SimpleDateFormat("dd-mm-yyyy").format(new Date()));
		
		empLoanList.add(empCardDetail);
		
		this.employeeServiceImpl.createEmployee(foundEmployee);
		
		
		Map<String,String> responseBody = new HashMap<>();
		responseBody.put("issueId", updatedItem.getIssueId());
		return new ResponseEntity<Map<String,String>>(responseBody, HttpStatus.OK);
	}

}
