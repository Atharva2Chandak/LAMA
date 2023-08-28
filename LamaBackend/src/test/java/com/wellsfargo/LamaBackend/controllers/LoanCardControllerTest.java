package com.wellsfargo.LamaBackend.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.wellsfargo.LamaBackend.dto.LoanCardDto;
import com.wellsfargo.LamaBackend.entities.LoanCard;
import com.wellsfargo.LamaBackend.service.impl.LoanCardServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LoanCardControllerTest {
	
	@Mock
	private LoanCardServiceImpl mockLoanCardService;
	
	@InjectMocks
	LoanCardController underTest;
	
	@Test
	void createEmployee_shouldCreateSuccessfully() {
		// given
        LoanCardDto expectedLoanCard = new LoanCardDto();
        expectedLoanCard.setLoanType("electric");
        expectedLoanCard.setDurationInYears(4);
        
        when(mockLoanCardService.createLoanCard(any())).thenReturn(expectedLoanCard);

        // when
        LoanCard loanCard = new LoanCard();
        loanCard.setDurationInYears(4);
        loanCard.setLoanType("electric");
        ResponseEntity<LoanCardDto> response = underTest.saveLoanCard(loanCard);
        LoanCardDto actual = response.getBody();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertEquals(expectedLoanCard, actual),
                () -> assertEquals(expectedLoanCard.getLoanType(), actual.getLoanType()),
                () -> assertEquals(expectedLoanCard.getDurationInYears(), actual.getDurationInYears())
        );
	}
	
	@Test
	void createEmployee_shouldReturnBadRequest() {
		//given
		when(mockLoanCardService.createLoanCard(any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required request body is missing"));
		
		//when
		LoanCard loanCard = new LoanCard();
		ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> mockLoanCardService.createLoanCard(loanCard));
	    String expectedMessage = "Required request body is missing";
	    String actualMessage = exception.getMessage();
	    
	    //then
	    Assertions.assertTrue(actualMessage.contains(expectedMessage));
	    Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());	
	}
}
