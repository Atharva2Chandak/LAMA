package com.wellsfargo.LamaBackend.jwtsecurity;

import java.util.Collection;
import java.util.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wellsfargo.LamaBackend.entities.ERole;
import com.wellsfargo.LamaBackend.entities.User;
import com.wellsfargo.LamaBackend.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUserDetails implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;


  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public JwtUserDetails(Long id, String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    
    this.password = password;
    this.authorities = authorities;
  }

  public static JwtUserDetails build(User user) {
	  
	  
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());
	  
		   
	  
	//taking name as username
    return new JwtUserDetails(user.getId(),user.getUsername() ,user.getPassword(),authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    JwtUserDetails user = (JwtUserDetails) o;
    return Objects.equals(username, user.username);
  }
}