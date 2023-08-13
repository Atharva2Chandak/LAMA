package com.wellsfargo.LamaBackend.service;

import java.util.List;

import org.springframework.web.server.ResponseStatusException;

import com.wellsfargo.LamaBackend.dto.LoanCardDto;
import com.wellsfargo.LamaBackend.entities.LoanCard;

public interface LoanCardService {
	LoanCardDto createLoanCard(LoanCard loanCard) throws ResponseStatusException;
	List<LoanCardDto> getAllLoanCards();
	LoanCardDto getLoanCard(String id) throws ResponseStatusException;
}
