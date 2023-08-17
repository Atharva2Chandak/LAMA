package com.wellsfargo.LamaBackend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.wellsfargo.LamaBackend.service.impl.LoanCardServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeServiceImpl employeeServiceImpl;
	
	@Autowired
	private LoanCardServiceImpl loanCardServiceImpl;
	
	@Autowired
	private ItemServiceImpl itemServiceImpl;
	
	@Autowired
	private ModelMapper modelMapper;
	
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
	
	@PostMapping("/takeLoan")
	public void mapLoan(@RequestBody Map<String, String> empLoanDetails) {
		String empId = empLoanDetails.get("empId");
		String loanId = empLoanDetails.get("loanId");
//		EmployeeGetDto empDto = this.employeeServiceImpl.getEmployee(empId);
//		Employee emp = modelMapper.map(empDto, Employee.class);
//		LoanCard loanCard = modelMapper.map(this.loanCardServiceImpl.getLoanCard(loanId), LoanCard.class);
		
		Employee emp = this.employeeServiceImpl.getEmployeeEntity(empId);
		LoanCard loanCard = this.loanCardServiceImpl.getLoanCardEntity(loanId);
		List<EmployeeCardDetail> empLoanList = emp.getEmployeeCardDetails();
		List<EmployeeCardDetail> loanEmpList = loanCard.getCardEmployeesDetail();
		
		EmployeeCardDetail empCardDetail = new EmployeeCardDetail(emp,loanCard,"17-08-2020");
//		curr.add(new EmployeeCardDetail(emp, loanCard,"17-08-47"));
		
		empLoanList.add(empCardDetail);
		loanEmpList.add(empCardDetail);
		
		this.employeeServiceImpl.createEmployee(emp);
		this.loanCardServiceImpl.createLoanCard(loanCard);
		
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
		if(!this.itemServiceImpl.issueItemToEmployee(foundItem, foundEmployee)) throw new ResponseStatusException(HttpStatus.CONFLICT);
		
		
		
		//Saving the updated found item
		Item updatedItem = this.itemServiceImpl.createItem(foundItem);
		
		//Saving the updated employee
		foundEmployee.getItems().add(foundItem);
		
		//To-do return date fetch from loan card duration
		/*
		 * Possible Steps:
		 * Get loan cards matching the item category
		 * Create this loan card to item mapping
		 * Create employee to loan card mapping (employee_card_details) save this loan card in employee EmployeeCardDetail list
		 */
		Map<String,String> responseBody = new HashMap<>();
		responseBody.put("issueId", updatedItem.getIssueId());
		return new ResponseEntity<Map<String,String>>(responseBody, HttpStatus.OK);
	}

}
