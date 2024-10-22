package budget.control.project.security;

import budget.control.project.dto.request.LoginUserDTO;
import budget.control.project.dto.request.RegisterUserDTO;
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

  public User signup(RegisterUserDTO registerUserDTO) {
    if (userRepository.findByUsername(registerUserDTO.getUsername()).isPresent()) {
      throw new DuplicateUserException(
          "User with username '" + registerUserDTO.getUsername() + "' already exists");
    }

    User user =
        new User(
            registerUserDTO.getUsername(), passwordEncoder.encode(registerUserDTO.getPassword()));

    return userRepository.save(user);
  }

  public User authenticate(LoginUserDTO loginUserDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginUserDTO.getUsername(), loginUserDTO.getPassword()));

    return userRepository.findByUsername(loginUserDTO.getUsername()).orElseThrow();
  }
}
