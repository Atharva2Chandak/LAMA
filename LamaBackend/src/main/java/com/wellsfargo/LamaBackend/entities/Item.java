package com.wellsfargo.LamaBackend.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item_master")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Item {
	
	@Id
	@GeneratedValue(generator="uuid", strategy=GenerationType.AUTO)
	@GenericGenerator(name="uuid", strategy="uuid2")
	@Column(name="item_id", unique=true, nullable=false)
	private String id;
	
	@NotBlank
	@Size(max=30)
	@Column(name="item_description", nullable=false, length=30)
	private String itemDescription;
	
	@NotNull
	@Column(name="issue_status", nullable=false)
	private char issueStatus;
	
	@NotBlank
	@Size(max=50)
	@Column(name="item_make", nullable=false, length=50)
	private String itemMake;
	
	@NotBlank
	@Size(max=50)
	@Column(name="item_category", nullable=false, length=50)
	private String itemCategory;
	
	@NotNull
	@Min(value = 1, message="Item valuation must not be null and greater than one")
	@Column(name="item_valuation", nullable=false)
	private int itemValuation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable=true)
	@JsonIgnore
	private Employee employee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_id", nullable=false)
	@JsonIgnore
	private LoanCard loanCard;
	
	@Column(name="issue_id")
	private String issueId;
	
	@Column(name="issue_Date")
	private Date issueDate;
	
	@Column(name="return_date")
	private Date returnDate;

	public Item(String id, String itemDescription, char issueStatus, String itemMake, String itemCategory,
			int itemValuation) {
		super();
		this.id = id;
		this.itemDescription = itemDescription;
		this.issueStatus = issueStatus;
		this.itemMake = itemMake;
		this.itemCategory = itemCategory;
		this.itemValuation = itemValuation;
	}
}
