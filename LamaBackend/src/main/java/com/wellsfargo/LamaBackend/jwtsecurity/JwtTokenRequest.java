package com.wellsfargo.LamaBackend.jwtsecurity;


import javax.validation.constraints.NotBlank;

public class JwtTokenRequest {
	@NotBlank
  private String username;

	@NotBlank
	private String password;

	public String getUsername() {
		System.out.println("username: "+username);
		return username;
	}

	public JwtTokenRequest(@NotBlank String username, @NotBlank String password) {
		super();
		System.out.println("username: "+username);
		this.username = username;
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}