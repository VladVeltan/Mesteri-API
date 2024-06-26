package meseriasiapi.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import meseriasiapi.domain.AuthenticationResponse;
import meseriasiapi.domain.User;
import meseriasiapi.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static meseriasiapi.exceptions.messages.Messages.THERE_ALREADY_IS_A_USER_WITH_THIS_EMAIL;
import static meseriasiapi.exceptions.messages.Messages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(User request) {
        System.out.println(request.getEmail());
        System.out.println(request.getFirstName());
        System.out.println(request.getLastName());
        System.out.println(request.getRating());
        System.out.println(request.getPhone());
        System.out.println(request.getEmail());
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .phone(request.getPhone())
                .rating(request.getRating())
                .creationDate(request.getCreationDate())
                .description(request.getDescription())
                .yearsOfExperience(request.getYearsOfExperience())
                .age(request.getAge())
                .categoriesOfInterest(request.getCategoriesOfInterest())
                .build();

        Optional<User> optionalUser=userRepository.findByEmail(user.getEmail());
        if(optionalUser.isEmpty()){
            user = userRepository.save(user);

            String token = jwtService.generateToken(user);

            return new AuthenticationResponse(token);
        }else{
            throw new EntityExistsException(THERE_ALREADY_IS_A_USER_WITH_THIS_EMAIL);
        }

    }

    public AuthenticationResponse authenticate(User request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Optional<User> user = userRepository.findByEmail(request.getUsername());
        if (user.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND);
        }
        String token = jwtService.generateToken(user.get());
        return new AuthenticationResponse(token);

    }
}
