package budget.control.project.controller;

import budget.control.project.dto.request.LoginUserDTO;
import budget.control.project.dto.request.RegisterUserDTO;
import budget.control.project.dto.response.LoginResponse;
import budget.control.project.model.User;
import budget.control.project.security.AuthenticationService;
import budget.control.project.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

  private final JwtService jwtService;

  private final AuthenticationService authenticationService;

  public AuthenticationController(
      JwtService jwtService, AuthenticationService authenticationService) {
    this.jwtService = jwtService;
    this.authenticationService = authenticationService;
  }

  @PostMapping("/signup")
  public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDTO) {
    User registeredUser = authenticationService.signup(registerUserDTO);

    return ResponseEntity.ok(registeredUser);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDTO) {
    User authenticatedUser = authenticationService.authenticate(loginUserDTO);

    String jwtToken = jwtService.generateToken(authenticatedUser);

    LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());

    return ResponseEntity.ok(loginResponse);
  }
}
