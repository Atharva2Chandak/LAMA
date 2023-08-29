package com.wellsfargo.LamaBackend.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.wellsfargo.LamaBackend.entities.Item;
import com.wellsfargo.LamaBackend.service.impl.ItemServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
	
	
	@Mock
	private ItemServiceImpl mockItemService;
	
	@InjectMocks
	ItemController underTest;
	
	@Test
	void createItem_shouldCreateSuccessfully() {
		
		//given
		Item item = new Item();
		item.setIssueStatus('0');
		item.setItemCategory("electric");
		item.setItemValuation(7825);
		when(mockItemService.createItem(any())).thenReturn(item);
		
		//when
		ResponseEntity<Item> response = underTest.saveItem(item);
		Item responseItem = response.getBody();
		
		//then
		assertAll(
				()-> assertNotNull(responseItem),
				()-> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
				()-> assertEquals(item, responseItem)
		);
	}
	
	@Test
	void createItem_shouldReturnBadRequest() {
		//given
		when(mockItemService.createItem(any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required request body is missing"));
		
		//when and then
		Item item = new Item();
		item.setIssueStatus('1');
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, ()-> underTest.saveItem(item));
		String expectedMessage = "Required request body is missing";
		String actualMessage = exception.getMessage();
		assertAll(
				()-> assertTrue(actualMessage.contains(expectedMessage)),
				()-> assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus())
		);
	}
	
}
