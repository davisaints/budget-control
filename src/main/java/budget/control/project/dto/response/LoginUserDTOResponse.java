package budget.control.project.dto.response;

public class LoginUserDTOResponse {

  private String token;

  private long expiresIn;

  public LoginUserDTOResponse(String token, long expiresIn) {
    this.token = token;
    this.expiresIn = expiresIn;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(long expiresIn) {
    this.expiresIn = expiresIn;
  }
}
