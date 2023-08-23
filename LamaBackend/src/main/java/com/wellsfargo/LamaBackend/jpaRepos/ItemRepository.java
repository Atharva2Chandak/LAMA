package com.wellsfargo.LamaBackend.jpaRepos;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellsfargo.LamaBackend.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
	List<Item> getItemByEmployeeIdAndIssueStatus(String employeeId, char issueStatus);
}
