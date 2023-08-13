package com.wellsfargo.LamaBackend.jpaRepos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellsfargo.LamaBackend.entities.LoanCard;

@Repository
public interface LoanCardRepository extends JpaRepository<LoanCard, String>{
}
