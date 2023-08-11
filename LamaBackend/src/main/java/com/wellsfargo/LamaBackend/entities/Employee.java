package com.wellsfargo.LamaBackend.entities;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="employee_master")
public class Employee {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="employee_id")
	private String id;
	
	@Column(name="employee_name")
	private String name;
	
	private String designation;
	private String department;
	private char gender;
	private Date dob;
	private Date doj;
	private char isAdmin;
	public Employee() {
		super();
	}
	
	public Employee(String name, String designation, String department, char gender, Date dob, Date doj) {
		super();
		this.name = name;
		this.designation = designation;
		this.department = department;
		this.gender = gender;
		this.dob = dob;
		this.doj = doj;
		this.isAdmin = '0';
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
