package com.kauan.GerenciamentoDeTarefas.dtos.user;

import com.kauan.GerenciamentoDeTarefas.entities.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDtoResponse {
    private Long id;
    private String name;
    private String login;

    public UserDtoResponse(UserEntity userEntity) {
        id = userEntity.getId();
        name = userEntity.getName();
        login = userEntity.getLogin();
    }
}