package com.objectcomputing.init;

import com.objectcomputing.auth.BcryptPasswordEncoder;
import com.objectcomputing.domain.Authority;
import com.objectcomputing.domain.User;
import com.objectcomputing.repository.AuthorityRepository;
import com.objectcomputing.repository.UserRepository;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import io.micronaut.security.authentication.providers.PasswordEncoder;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Singleton

public class BootStrap  implements ApplicationEventListener<ServerStartupEvent> {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BcryptPasswordEncoder passwordEncoder;

    public BootStrap( UserRepository userRepository, AuthorityRepository authorityRepository,BcryptPasswordEncoder passwordEncoder) {
        this.userRepository=userRepository;
        this.authorityRepository=authorityRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        Authority admin_authority =null;
        Optional<Authority> optional_authority = authorityRepository.findByName("ROLE_ADMIN");
        if (optional_authority.isPresent()) {
            admin_authority = optional_authority.get();
        } else {
            admin_authority = new Authority();
            admin_authority.setName("ROLE_ADMIN");
            authorityRepository.save(admin_authority);
        }
        Optional<Authority> optional_userRole = authorityRepository.findByName("ROLE_USER");
        Authority user_authority = null;
        if (optional_userRole.isPresent()) {
            user_authority = optional_userRole.get();
        } else {
            user_authority = new Authority();
            user_authority.setName("ROLE_USER");
            authorityRepository.save(user_authority);

            Authority authority = new Authority();
            authority.setName("ROLE_ANONYMOUS");
            authorityRepository.save(authority);
        }

        createUser("admin", "password", new HashSet<Authority>(Arrays.asList(admin_authority)));
        createUser("acme", "password", new HashSet<Authority>(Arrays.asList(user_authority)));
        createUser("makers", "password", new HashSet<Authority>(Arrays.asList(user_authority)));

    }


    private void createUser(String username, String password, Set<Authority> authoritySet) {
        Optional<User> optional_user = userRepository.findByLogin(username);
        User user = null;
        if (optional_user.isPresent()) {
            user = optional_user.get();
        } else {
            user = new User();
            user.setEmail(username+"@t.t");
            user.setLogin(username);
            user.setFirstName(username);
            user.setFirstName("Account");
            user.setPassword(passwordEncoder.encode(password));
            user.setActivated(true);
            user.setAuthorities(authoritySet);
            userRepository.save(user);
        }
    }
}