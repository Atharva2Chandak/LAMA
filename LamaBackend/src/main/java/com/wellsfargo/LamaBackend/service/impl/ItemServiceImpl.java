package com.wellsfargo.LamaBackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

//import com.wellsfargo.LamaBackend.dto.EmployeeGetDto;
//import com.wellsfargo.LamaBackend.dto.EmployeePostDto;
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
        //?? return this.modelMapper.map(savedEmployee, EmployeePostDto.class);
	}
	
	public Item getItem(String id) throws ResponseStatusException {
		Optional<Item> item = this.itemRepository.findById(id);
		if(item.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		//?? EmployeeGetDto employeeGetDto = this.modelMapper.map(employee.get(), EmployeeGetDto.class);
        return item.get();
	}
	
	public List<Item> getAllItems() {
		//List<Item> item = this.itemRepository.findAll();
		List<Item> foundItems = new ArrayList<>();
		/*for(var item: items) {
            //??foundEmployeesDto.add(this.modelMapper.map(employee, EmployeeGetDto.class));
		}*/
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
				
				if(key.equalsIgnoreCase("issueStatus")) {
					foundItem.setIssueStatus(updateValue.charAt(0));
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
			//return this.modelMapper.map(updatedEmployee, EmployeePostDto.class);
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
}
