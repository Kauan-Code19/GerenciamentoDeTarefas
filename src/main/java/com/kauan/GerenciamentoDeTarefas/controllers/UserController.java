package com.kauan.GerenciamentoDeTarefas.controllers;

import com.kauan.GerenciamentoDeTarefas.dtos.user.UserDto;
import com.kauan.GerenciamentoDeTarefas.dtos.user.UserDtoResponse;
import com.kauan.GerenciamentoDeTarefas.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDtoResponse> createUser(@Valid @RequestBody UserDto userDto) {
        UserDtoResponse userDtoResponse = userService.createUser(userDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(userDtoResponse.getId()).toUri();

        return ResponseEntity.created(uri).body(userDtoResponse);
    }
}