package budget.control.project.security;

import budget.control.project.dto.request.LoginUserDTORequest;
import budget.control.project.dto.request.RegisterUserDTORequest;
import budget.control.project.dto.response.RegisterUserDTOResponse;
import budget.control.project.exception.DuplicateUserException;
import budget.control.project.model.User;
import budget.control.project.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public RegisterUserDTOResponse signup(RegisterUserDTORequest registerUserDTORequest) {
    if (userRepository.findByUsername(registerUserDTORequest.getUsername()).isPresent()) {
      throw new DuplicateUserException(
          "User with username '" + registerUserDTORequest.getUsername() + "' already exists");
    }

    User user =
        new User(
            registerUserDTORequest.getUsername(), passwordEncoder.encode(registerUserDTORequest.getPassword()));

    return new RegisterUserDTOResponse(userRepository.save(user));
  }

  public User authenticate(LoginUserDTORequest loginUserDTORequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginUserDTORequest.getUsername(), loginUserDTORequest.getPassword()));

    return userRepository.findByUsername(loginUserDTORequest.getUsername()).orElseThrow();
  }
}
