package com.wellsfargo.LamaBackend.dto;

public class LoanCardDto {
	private String id;
	private String loanType;
	private int durationInYears;
	
	public LoanCardDto() {}

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
	};
}
