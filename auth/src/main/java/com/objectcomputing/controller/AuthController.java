package com.objectcomputing.controller;

import com.objectcomputing.domain.User;
import com.objectcomputing.repository.UserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

@Controller("/auth")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
    @Get(uri = "/", produces = MediaType.APPLICATION_JSON)
    public Map index() {
        return Map.of(
                "test", test,
                "foo", foo
        );
    }
    */

    @Get("/users")
    public HttpResponse getUsers() {
        return HttpResponse.ok(
                userRepository.findAll()
        );
    }

    @Get("/user/{id}")
    public HttpResponse getUser(Long id) {
        return HttpResponse.ok(
                userRepository.findById(id)
        );
    }
    
    @Post("/user")
    public HttpResponse saveUser(User user) {
        return HttpResponse.created(
                userRepository.save(user)
        );
    }
}
