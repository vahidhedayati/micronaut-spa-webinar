package com.objectcomputing.auth;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;

@Secured("isAuthenticated()")
@Controller("/users")
public class UserController {

    private final UserClient userClient;

    UserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @Get("/")
    public HttpResponse list() {
        return userClient.allusers();
    }

    @Get("/{id}")
    public HttpResponse findUser(Long id) {
        return userClient.user(id);
    }

}
