package budget.control.project.dto.request;

import jakarta.validation.constraints.NotBlank;

public class LoginUserDTORequest {

  @NotBlank(message = "Username is mandatory")
  private String username;

  @NotBlank(message = "Password is mandatory")
  private String password;

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
}
