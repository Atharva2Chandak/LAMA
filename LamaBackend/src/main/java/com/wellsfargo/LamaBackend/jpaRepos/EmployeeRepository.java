package com.wellsfargo.LamaBackend.jpaRepos;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellsfargo.LamaBackend.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
}
