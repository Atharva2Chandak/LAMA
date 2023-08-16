package com.wellsfargo.LamaBackend.controllers;

import java.util.List;
import java.util.Map;

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

//import com.wellsfargo.LamaBackend.dto.LoanCardDto;
//import com.wellsfargo.LamaBackend.dto.LoanCardDto;
import com.wellsfargo.LamaBackend.entities.Item;
import com.wellsfargo.LamaBackend.service.impl.ItemServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/api/item")
public class ItemController {

	@Autowired
	private ItemServiceImpl itemServiceImpl;
	
	@PostMapping("/create")
	public ResponseEntity<Item> saveItem(@RequestBody Item item) throws ResponseStatusException {
		try {			
			//LoanCardDto loanCardDto = this.loanCardServiceImpl.createLoanCard(loanCard);
            Item savedItem = this.itemServiceImpl.createItem(item);
			return new ResponseEntity<Item>(savedItem, HttpStatus.CREATED);
		} catch(ResponseStatusException e) {
			throw e;
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItem(@PathVariable String id) {
		try {
			return new ResponseEntity<Item>(this.itemServiceImpl.getItem(id), HttpStatus.OK);
		} catch (ResponseStatusException e) {
			throw e;
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Item>> getAllItems() {
		List<Item> savedItem = this.itemServiceImpl.getAllItems();
		return new ResponseEntity<List<Item>>(savedItem, HttpStatus.OK); 
	}
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<Item> updateItem(@PathVariable String id, @RequestBody Map<String, String> item) throws ResponseStatusException {
		try {
			return new ResponseEntity<Item>(this.itemServiceImpl.patchItem(id, item), HttpStatus.OK);
		} catch(ResponseStatusException e) {
			throw e;
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteItem(@PathVariable String id) throws ResponseStatusException{
		try {
			return new ResponseEntity<Boolean>(this.itemServiceImpl.deleteItem(id), HttpStatus.OK);
		} catch(ResponseStatusException e) {
			throw e;
		}
	}
}