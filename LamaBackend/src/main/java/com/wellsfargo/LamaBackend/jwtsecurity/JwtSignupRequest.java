package com.wellsfargo.LamaBackend.jwtsecurity;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class JwtSignupRequest {
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	private Set<String> role;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
	

	public JwtSignupRequest(@NotBlank @Size(min = 3, max = 20) String username,
			@NotBlank @Size(min = 6, max = 40) String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
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

	public Set<String> getRole() {
		return this.role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
}