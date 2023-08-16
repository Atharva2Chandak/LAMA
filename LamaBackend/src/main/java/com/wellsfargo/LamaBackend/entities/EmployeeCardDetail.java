package com.wellsfargo.LamaBackend.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employee_card_details")
public class EmployeeCardDetail {
	
	@Id
	@GeneratedValue
	@Column(name="employee_card_detail_id")
	private long employeeCardDetailId; //Primary key for this entity
	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "loan_id")
	private LoanCard loanCard;
	
	@Column(name="card_issue_date", nullable = false)
	private Date cardIssueDate;

	public EmployeeCardDetail() {
		super();
	}
	
	public EmployeeCardDetail(Employee employee, LoanCard loanCard, String cardIssueDate) {
		super();
		this.employee = employee;
		this.loanCard = loanCard;
		this.cardIssueDate = this.parseDate(cardIssueDate);
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LoanCard getLoanCard() {
		return loanCard;
	}

	public void setLoanCard(LoanCard loanCard) {
		this.loanCard = loanCard;
	}

	public Date getCardIssueDate() {
		return cardIssueDate;
	}

	public void setCardIssueDate(String cardIssueDate) {
		this.cardIssueDate = this.parseDate(cardIssueDate);
	}
	
	private Date parseDate(String date) {
		try {
			return new Date(new SimpleDateFormat("dd-MM-yyyy").parse(date).getTime());
		} catch(ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
}
