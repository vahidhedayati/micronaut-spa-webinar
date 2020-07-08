package com.objectcomputing.auth;

import com.objectcomputing.domain.Authority;
import com.objectcomputing.domain.User;
import com.objectcomputing.repository.UserRepository;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.providers.PasswordEncoder;
import io.micronaut.validation.validator.constraints.EmailValidator;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class DatabaseAuthenticationProvider implements AuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(DatabaseAuthenticationProvider.class);
    private final UserRepository userRepository;
    private final BcryptPasswordEncoder passwordEncoder;

    public DatabaseAuthenticationProvider(UserRepository userRepository, BcryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        System.out.println("aa");
        String username = authenticationRequest.getIdentity().toString();

        log.debug("Authenticating {}", username);
        if (new EmailValidator().isValid(username, null)) {
            return Flowable.just(userRepository.findByEmail(username)
                    .filter(user -> passwordEncoder.matches(authenticationRequest.getSecret().toString(), user.getPassword()))
                    .map(user -> createMicronautSecurityUser(username, user))
                    .orElse(new NotAuthenticatedResponse("Invalid username or password")));
        }

        String lowercaseLogin = username.toLowerCase(Locale.ENGLISH);

        /**
         * Below is a test block of actual call further down. Comment out other return below and enable this block for
         * a demonstration on where bottleneck is. There is a period of time before authentication succeeds / fails
         * it turns out there is some time consued in passwordEncoder.matches
         *
        Optional<User> u = userRepository.findByLogin(lowercaseLogin);
        if (u.isPresent()) {
            User user1 = u.get();
            System.out.println("Got user "+user1.getLogin());
            //paswordEncoder.matches seems to slow down authentication process
            boolean slowProcessValidation = passwordEncoder.matches(authenticationRequest.getSecret().toString(), user1.getPassword());
            System.out.println("Password validation "+slowProcessValidation);
            if (slowProcessValidation) {
                if (!user1.getActivated()) {
                    System.out.println("is not activated");
                }
                List<String> grantedAuthorities = user1.getAuthorities().stream()
                        .map(Authority::getName)
                        .collect(Collectors.toList());
                return Flowable.just(new CustomUserDetails(user1.getLogin(), grantedAuthorities));
            }
        } else {
            return Flowable.just(new NotAuthenticatedResponse("Invalid username or password"));
        }
        */


        return Flowable.just(userRepository.findByLogin(lowercaseLogin)
                .filter(user -> passwordEncoder.matches(authenticationRequest.getSecret().toString(), user.getPassword()))
                .map(user -> createMicronautSecurityUser(lowercaseLogin, user))
                .orElse(new NotAuthenticatedResponse("Invalid username or password")));
    }

    private AuthenticationResponse createMicronautSecurityUser(String lowercaseLogin, User user) {
        if (!user.getActivated()) {
            return new NotAuthenticatedResponse("User " + lowercaseLogin + " was not activated");
        }
        List<String> grantedAuthorities = user.getAuthorities().stream()
                .map(Authority::getName)
                .collect(Collectors.toList());

        return new CustomUserDetails(user.getLogin(), grantedAuthorities);
    }
}