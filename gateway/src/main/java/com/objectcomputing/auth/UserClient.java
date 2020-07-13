package com.objectcomputing.auth;

import com.objectcomputing.domain.User;
import com.objectcomputing.service.dto.UserDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;


@Client(id = "auth", path = "/user")
public interface UserClient {

    @Get(uri = "/allusers")
    HttpResponse allusers();

    @Get("/user/{id}")
    HttpResponse user(Long id);


    @Post("/saveuser")
    HttpResponse user(User user);

    HttpResponse<User> createUser(UserDTO userDTO);

}
