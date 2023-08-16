package com.wellsfargo.LamaBackend.dto;

import java.sql.Date;

public class EmployeeGetDto {
	private String name;
	private String designation;
	private char gender;
	private Date dob;
	private Date doj;
	private String id;
	
	public EmployeeGetDto() {
		super();
	}

	public String getName() {
		return name;
	}
	public String getId() { return id; }

	public void setId(String id) {
		this.id = id;
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

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}
}
