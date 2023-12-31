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

import com.wellsfargo.LamaBackend.entities.Item;
import com.wellsfargo.LamaBackend.service.impl.ItemServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/api/item")
public class ItemController {

	@Autowired
	private ItemServiceImpl itemServiceImpl;
		
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<Item> saveItem(@Valid @RequestBody Item item) throws ResponseStatusException {
		if(!(item.getIssueStatus() == '0' || item.getIssueStatus() == '1')) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Issue Status must be either 0 or 1");
		}
		try {						
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/update/{id}")
	public ResponseEntity<Item> updateItem(@PathVariable String id, @RequestBody Map<String, String> item) throws ResponseStatusException {
		try {
			return new ResponseEntity<Item>(this.itemServiceImpl.patchItem(id, item), HttpStatus.OK);
		} catch(ResponseStatusException e) {
			throw e;
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteItem(@PathVariable String id) throws ResponseStatusException{
		try {
			return new ResponseEntity<Boolean>(this.itemServiceImpl.deleteItem(id), HttpStatus.OK);
		} catch(ResponseStatusException e) {
			throw e;
		}
	}
}