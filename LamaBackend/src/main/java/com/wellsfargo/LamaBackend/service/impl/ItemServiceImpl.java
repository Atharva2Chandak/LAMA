package com.wellsfargo.LamaBackend.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wellsfargo.LamaBackend.entities.Employee;
import com.wellsfargo.LamaBackend.entities.Item;
import com.wellsfargo.LamaBackend.jpaRepos.ItemRepository;
import com.wellsfargo.LamaBackend.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	public Item createItem(Item item) {
		Item savedItem = itemRepository.save(item);
		return savedItem;
	}
	
	public Item getItem(String id) throws ResponseStatusException {
		Optional<Item> item = this.itemRepository.findById(id);
		if(item.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return item.get();
	}
	
	public List<Item> getAllItems() {
		List<Item> foundItems = this.itemRepository.findAll();
		return foundItems;
	}
	
	public Item patchItem(String id, Map<String ,String> item) throws ResponseStatusException {
		
		Optional<Item> dbEmp = this.itemRepository.findById(id);
		if(dbEmp.isPresent()) {
			Item foundItem = dbEmp.get();
			
			for(String key: item.keySet()) {
				
				String updateValue = item.get(key);
				
				//To-do validate updateValue
				if(key.equalsIgnoreCase("itemDescription")) {
					foundItem.setItemDescription(updateValue);
				}
				
				if(key.equalsIgnoreCase("itemMake")) {
					foundItem.setItemMake(updateValue);
				}
				
				if(key.equalsIgnoreCase("itemCategory")) {
					foundItem.setItemCategory(updateValue);
				}
				
				if(key.equalsIgnoreCase("itemValuation")) {
					foundItem.setItemValuation(updateValue.charAt(0));
				}				
			}
			
			Item updatedItem = this.itemRepository.save(foundItem);
            return updatedItem;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item not found");
		}
	}
	
	public Boolean deleteItem(String id) {
		if(id == null) return false;
			this.itemRepository.deleteById(id);
		return true;
	}
	
	public Boolean issueItemToEmployee(Item item, Employee employee) {
		if(item.getIssueStatus() == '1') return false;
		UUID uuid = UUID.randomUUID();
		item.setIssueId(uuid.toString());
		item.setEmployee(employee);
		item.setIssueDate(new Date());
		item.setIssueStatus('1');
		return true;
	}
}
