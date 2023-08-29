package com.wellsfargo.LamaBackend.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="loan_card_master")
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class LoanCard {
	
	@Id
	@GeneratedValue(generator="uuid", strategy=GenerationType.AUTO)
	@GenericGenerator(name="uuid", strategy="uuid2")
	@Column(name="loan_id", unique=true, nullable=false)
	private String id;
	
	
	@NotBlank(message="Loan Type cannot be null or blank")
	@Size(min=3, max=50)
	@Column(name="loan_type", nullable=false,unique=true, length=50)
	private String loanType;
	
	@NotNull
	@Min(value=1,message="Duration can't be less than a year")
	@Column(name="duration_in_years", nullable=false)
	private int durationInYears;
	
	@OneToMany(mappedBy = "loanCard", cascade = CascadeType.ALL)
	private List<EmployeeCardDetail> cardEmployeesDetail; //one card, many employees
	
	@OneToMany(mappedBy = "loanCard", cascade = CascadeType.ALL)
	private Set<Item> items;
	
	public LoanCard(String loanType, int durationInYears) {
		super();
		this.loanType = loanType;
		this.durationInYears = durationInYears;
		this.cardEmployeesDetail = new ArrayList<>();
	}
}
