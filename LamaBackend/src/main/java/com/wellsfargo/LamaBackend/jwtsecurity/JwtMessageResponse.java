package com.wellsfargo.LamaBackend.jwtsecurity;

public class JwtMessageResponse {
  private String message;

  public JwtMessageResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
