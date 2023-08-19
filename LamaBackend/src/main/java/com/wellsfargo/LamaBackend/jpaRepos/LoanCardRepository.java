package com.wellsfargo.LamaBackend.jpaRepos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellsfargo.LamaBackend.entities.LoanCard;

@Repository
public interface LoanCardRepository extends JpaRepository<LoanCard, String>{
	Optional<LoanCard> findByLoanType(String loanType);
}
