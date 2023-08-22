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

@Entity
@Table(name="employee_master")
public class Employee {
	
	@Id
	@GeneratedValue(generator="uuid", strategy=GenerationType.AUTO)
	@GenericGenerator(name="uuid", strategy="uuid2")
	@Column(name="employee_id", unique=true, nullable=false)
	private String id;
	
	@NotBlank(message="name cannot be left blank")
	@Column(name="employee_name", nullable=false)
	private String name;
	
	@NotBlank
	@Column(nullable=false)
	@Size(min=8, max=20, message="The password must be between 8 and 20 characters")
	private String password;
	
	private String designation;
	private String department;
	
	@NotNull
	private char gender;
	
	private Date dob;
	private Date doj;
	
	@Column(name="is_admin")
	private char isAdmin;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<EmployeeCardDetail> empoyeeCardDetails; //one employee, many cards
	
	@OneToMany(mappedBy = "employee")
	private Set<Item> items;
	
	public Employee() {
		super();
	}
	
	public Employee(String name, String designation, String password, String department, char gender, Date dob, Date doj) {
		super();
		this.name = name;
		this.designation = designation;
		this.department = department;
		this.password = password;
		this.gender = gender;
		this.dob = dob;
		this.doj = doj;
		this.isAdmin = '0';
		this.empoyeeCardDetails = new ArrayList<>();
		this.items = new HashSet<>();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(String dob) {
		try {
			this.dob = this.parseDate(dob);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			this.dob = null;
		}
	}
	public Date getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		try {
			this.doj = this.parseDate(doj);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			this.doj = null;
		}
	}
	public char getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(char isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public List<EmployeeCardDetail> getEmployeeCardDetails() {
		return this.empoyeeCardDetails;
	}
	
	public void setEmployeeCardDetails(List<EmployeeCardDetail> employeeCardDetails) {
		this.empoyeeCardDetails = employeeCardDetails;
	}
	
	
	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	private Date parseDate(String date) {
		try {
			return new Date(new SimpleDateFormat("dd-MM-yyyy").parse(date).getTime());
		} catch(ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", designation=" + designation + ", department=" + department
				+ ", gender=" + gender + ", dob=" + dob + ", doj=" + doj + ", isAdmin=" + isAdmin + "]";
	}
	
	
}
