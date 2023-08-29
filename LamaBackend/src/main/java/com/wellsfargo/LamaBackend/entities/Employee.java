package com.wellsfargo.LamaBackend.entities;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="employee_master")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Employee {
	
	@Id
	@Column(name="employee_id", unique=true, nullable=false)

	private String id;
	
	@NotBlank(message="name cannot be left blank")
	@Column(name="employee_name", nullable=false)
	private String name;
	
	@NotBlank
	@Column(nullable=false)
	@Size(min=8, message="The password must be between 8 and 20 characters")
	private String password;
	
	private String designation;
	private String department;
	
	@NotNull
	private char gender;
	
	@Setter(AccessLevel.NONE)
	private Date dob;
	
	@Setter(AccessLevel.NONE)
	private Date doj;
	
	@Column(name="is_admin")
	private char isAdmin;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<EmployeeCardDetail> employeeCardDetails; //one employee, many cards
	
	@OneToMany(mappedBy = "employee")
	private Set<Item> items;

	
	public Employee(String id, String name, String designation, String password, String department, char gender, Date dob, Date doj) {
		super();
		this.id = id;
		this.name = name;
		this.designation = designation;
		this.department = department;
		this.password = password;
		this.gender = gender;
		this.dob = dob;
		this.doj = doj;
		this.isAdmin = '0';
		this.employeeCardDetails = new ArrayList<>();
		this.items = new HashSet<>();
	}
	
	public void setDob(String dob) {
		try {
			this.dob = this.parseDate(dob);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			this.dob = null;
		}
	}

	public void setDoj(String doj) {
		try {
			this.doj = this.parseDate(doj);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			this.doj = null;
		}
	}
	
	private Date parseDate(String date) {
		try {
			return new Date(new SimpleDateFormat("dd-MM-yyyy").parse(date).getTime());
		} catch(ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}	
}
