package com.objectcomputing.init;

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

@Singleton

public class BootStrap  implements ApplicationEventListener<ServerStartupEvent> {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public BootStrap( UserRepository userRepository, AuthorityRepository authorityRepository,PasswordEncoder passwordEncoder) {
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
            Authority authority = new Authority();
            authority.setName("ROLE_USER");
            authorityRepository.save(authority);
            authority = new Authority();
            authority.setName("ROLE_ANONYMOUS");
            authorityRepository.save(authority);
        }

        Optional<User> optional_user = userRepository.findByLogin("admin");
        User user = null;
        if (optional_user.isPresent()) {
            user = optional_user.get();
        } else {
            user = new User();
            user.setEmail("t@t.t");
            user.setLogin("admin");
            user.setFirstName("Admin");
            user.setFirstName("Account");
            user.setPassword(passwordEncoder.encode("password"));
            user.setActivated(true);
            user.setAuthorities(new HashSet<Authority>(Arrays.asList(admin_authority)));
            userRepository.save(user);
        }
    }

}