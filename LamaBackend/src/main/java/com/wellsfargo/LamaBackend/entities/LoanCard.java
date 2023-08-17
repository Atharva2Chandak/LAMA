package com.wellsfargo.LamaBackend.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="loan_card_master")
public class LoanCard {
	
	@Id
	@GeneratedValue(generator="uuid", strategy=GenerationType.AUTO)
	@GenericGenerator(name="uuid", strategy="uuid2")
	@Column(name="loan_id", unique=true, nullable=false)
	private String id;
	
	
	@Column(name="loan_type", nullable=false)
	private String loanType;
	
	@Column(name="duration_in_years", nullable=false)
	private int durationInYears;
	
	@OneToMany(mappedBy = "loanCard", cascade = CascadeType.ALL)
	private List<EmployeeCardDetail> cardEmployeesDetail; //one card, many employees
	
	public LoanCard() {}
	
	public LoanCard(String loanType, int durationInYears) {
		super();
		this.loanType = loanType;
		this.durationInYears = durationInYears;
		this.cardEmployeesDetail = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public int getDurationInYears() {
		return durationInYears;
	}

	public void setDurationInYears(int durationInYears) {
		this.durationInYears = durationInYears;
	}

	
	public List<EmployeeCardDetail> getCardEmployeesDetail() {
		return cardEmployeesDetail;
	}

	public void setCardEmployeesDetail(List<EmployeeCardDetail> cardEmployeesDetail) {
		this.cardEmployeesDetail = cardEmployeesDetail;
	}

	@Override
	public String toString() {
		return "LoanCard [id=" + id + ", loanType=" + loanType + ", durationInYears=" + durationInYears + "]";
	}
}
