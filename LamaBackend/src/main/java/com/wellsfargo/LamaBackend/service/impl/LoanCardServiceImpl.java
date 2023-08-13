package com.wellsfargo.LamaBackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wellsfargo.LamaBackend.dto.LoanCardDto;
import com.wellsfargo.LamaBackend.entities.LoanCard;
import com.wellsfargo.LamaBackend.jpaRepos.LoanCardRepository;
import com.wellsfargo.LamaBackend.service.LoanCardService;

@Service
public class LoanCardServiceImpl implements LoanCardService{
	
	@Autowired
	private LoanCardRepository loanCardRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public LoanCardDto createLoanCard(LoanCard loanCard) throws ResponseStatusException {	
		if(loanCard == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Loan Card cannot be null");
		System.out.println("Here");
		LoanCard savedLoanCard = this.loanCardRepository.save(loanCard);
		return this.modelMapper.map(savedLoanCard, LoanCardDto.class);
	}
	
	public List<LoanCardDto> getAllLoanCards() {
		List<LoanCardDto> loanCardDtos = new ArrayList<>();
		for(var loanCard: this.loanCardRepository.findAll()) {
			loanCardDtos.add(this.modelMapper.map(loanCard, LoanCardDto.class));
		}
		return loanCardDtos;
	}
	
	public LoanCardDto getLoanCard(String id) throws ResponseStatusException {
		if(id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id can't be null");
		Optional<LoanCard> loanCard = this.loanCardRepository.findById(id);
		if(loanCard.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan Card Not found");
		return this.modelMapper.map(loanCard.get(), LoanCardDto.class);
	}
	
	//To-do Update loan, delete loan
}
