package com.wellsfargo.LamaBackend.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
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
import com.wellsfargo.LamaBackend.entities.LoanCard;
import com.wellsfargo.LamaBackend.jpaRepos.ItemRepository;
import com.wellsfargo.LamaBackend.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private LoanCardServiceImpl loanCardService;
	
	
	public Item createItem(Item item) {
		
		/*
		 * An item can't be saved without an associated loan card
		 * Fetching the corresponding loan card. 
		 * Item to loan card is many to one mapping
		 **/
		
		//If loan Card is not found suitable exception is thrown by the loan card service method used below
		LoanCard associatedLoanCard = this.loanCardService.getLoanCardEntityByLoanType(item.getItemCategory());
		item.setLoanCard(associatedLoanCard); //Set associated loan card for the current item
		Item savedItem = itemRepository.save(item);
		return savedItem;
	}
	
	public Item getItem(String id) throws ResponseStatusException {
		Optional<Item> item = this.itemRepository.findById(id);
		if(item.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested item not found");
        return item.get();
	}
	
	public List<Item> getAllItems() {
		List<Item> foundItems = this.itemRepository.findAll();
		return foundItems;
	}
	
	public Item patchItem(String id, Map<String ,String> item) throws ResponseStatusException {
		
		Optional<Item> dbItem = this.itemRepository.findById(id);
		if(dbItem.isPresent()) {
			Item foundItem = dbItem.get();
			
			for(String key: item.keySet()) {
				
				String updateValue = item.get(key);
				
				//To-do validate updateValue
				if(key.equalsIgnoreCase("itemCategory")) {
					/*
					 * Checking if the new value for item category has a loan card associated
					 * If available updating the loanCard for this item as well
					 */
					LoanCard newLoanCard = this.loanCardService.getLoanCardEntityByLoanType(updateValue);
					foundItem.setLoanCard(newLoanCard);
					foundItem.setItemCategory(updateValue);
				}
				
				if(key.equalsIgnoreCase("itemDescription")) {
					foundItem.setItemDescription(updateValue);
				}
				
				if(key.equalsIgnoreCase("itemMake")) {
					foundItem.setItemMake(updateValue);
				}
				
				if(key.equalsIgnoreCase("itemValuation")) {
					foundItem.setItemValuation(Integer.parseInt(updateValue));
				}				
			}
			
			//If exception is raised anywhere while updating, then no updates are done.
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
		
		LocalDate currDate = LocalDate.now();
		ZoneId zoneId = ZoneId.systemDefault();

        Date issueDate = Date.from(currDate.atStartOfDay(zoneId).toInstant());
		item.setIssueDate(issueDate);
		
		LocalDate returnDateLocal = currDate.plusYears(item.getLoanCard().getDurationInYears());
		Date returnDate = Date.from(returnDateLocal.atStartOfDay(zoneId).toInstant());
		
		item.setReturnDate(returnDate);
		
		item.setIssueStatus('1');
		return true;
	}
}
