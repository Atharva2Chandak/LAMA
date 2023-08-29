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

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "employee_card_details")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
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
	
	@Setter(AccessLevel.NONE)
	@Column(name="card_issue_date", nullable = false)
	private Date cardIssueDate;

	public EmployeeCardDetail(Employee employee, LoanCard loanCard, String cardIssueDate) {
		super();
		this.employee = employee;
		this.loanCard = loanCard;
		this.cardIssueDate = this.parseDate(cardIssueDate);
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
