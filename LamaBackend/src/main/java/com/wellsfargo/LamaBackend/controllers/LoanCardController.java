package com.wellsfargo.LamaBackend.controllers;

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

import com.wellsfargo.LamaBackend.dto.LoanCardDto;
import com.wellsfargo.LamaBackend.entities.LoanCard;
import com.wellsfargo.LamaBackend.service.impl.LoanCardServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/api/loan")
public class LoanCardController {

	@Autowired
	private LoanCardServiceImpl loanCardServiceImpl;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<LoanCardDto> saveLoanCard(@Valid @RequestBody LoanCard loanCard) throws ResponseStatusException {
		try {			
			LoanCardDto loanCardDto = this.loanCardServiceImpl.createLoanCard(loanCard);
			return new ResponseEntity<LoanCardDto>(loanCardDto, HttpStatus.CREATED);
		} catch(ResponseStatusException e) {
			throw e;
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<LoanCardDto> getLoanCard(@PathVariable String id) {
		try {
			return new ResponseEntity<LoanCardDto>(this.loanCardServiceImpl.getLoanCard(id), HttpStatus.OK);
		} catch (ResponseStatusException e) {
			throw e;
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<LoanCardDto>> getAllLoanCards() {
		List<LoanCardDto> loanCardDtos = this.loanCardServiceImpl.getAllLoanCards();
		return new ResponseEntity<List<LoanCardDto>>(loanCardDtos, HttpStatus.OK); 
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/update/{id}")
	public ResponseEntity<LoanCardDto> updateLoanCard(@PathVariable String id, @RequestBody Map<String, String> loanCard) throws ResponseStatusException {
		try {
			return new ResponseEntity<LoanCardDto>(this.loanCardServiceImpl.patchLoanCard(id, loanCard), HttpStatus.OK);
		} catch(ResponseStatusException e) {
			throw e;
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteLoanCard(@PathVariable String id) throws ResponseStatusException{
		try {
			return new ResponseEntity<Boolean>(this.loanCardServiceImpl.deleteLoanCard(id), HttpStatus.OK);
		} catch(ResponseStatusException e) {
			throw e;
		}
	}
}