package budget.control.project.controller;

import budget.control.project.dto.request.LoginUserDTORequest;
import budget.control.project.dto.request.RegisterUserDTORequest;
import budget.control.project.dto.response.LoginUserDTOResponse;
import budget.control.project.dto.response.RegisterUserDTOResponse;
import budget.control.project.model.User;
import budget.control.project.security.AuthenticationService;
import budget.control.project.security.JwtService;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<RegisterUserDTOResponse> register(
      @RequestBody RegisterUserDTORequest registerUserDTORequest) {
    return new ResponseEntity<>(
        authenticationService.signup(registerUserDTORequest), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginUserDTOResponse> authenticate(
      @RequestBody LoginUserDTORequest loginUserDTORequest) {
    User authenticatedUser = authenticationService.authenticate(loginUserDTORequest);

    String jwtToken = jwtService.generateToken(authenticatedUser);

    LoginUserDTOResponse loginUserDTOResponse =
        new LoginUserDTOResponse(jwtToken, jwtService.getExpirationTime());

    return new ResponseEntity<>(loginUserDTOResponse, HttpStatus.OK);
  }
}
