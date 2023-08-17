package com.wellsfargo.LamaBackend.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.server.ResponseStatusException;

import com.wellsfargo.LamaBackend.entities.Employee;
//import com.wellsfargo.LamaBackend.dto.EmployeeGetDto;
//import com.wellsfargo.LamaBackend.dto.EmployeePostDto;
import com.wellsfargo.LamaBackend.entities.Item;

public interface ItemService {
	Item createItem(Item item);
	Item getItem(String id) throws ResponseStatusException;
	List<Item> getAllItems();
	Item patchItem(String id, Map<String, String> item) throws ResponseStatusException;
	Boolean deleteItem(String id);
	Boolean issueItemToEmployee(Item item, Employee employee);
}