package com.kauan.GerenciamentoDeTarefas.services.login;

import com.kauan.GerenciamentoDeTarefas.dtos.user.UserDto;
import com.kauan.GerenciamentoDeTarefas.dtos.user.UserDtoResponse;
import com.kauan.GerenciamentoDeTarefas.entities.user.UserEntity;
import com.kauan.GerenciamentoDeTarefas.repositories.UserRepository;
import com.kauan.GerenciamentoDeTarefas.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {
    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserDtoResponse signIn(UserDto userDto) {
        UserEntity userEntity = userRepository.findByLoginAndPassword(userDto.getLogin(), userDto.getPassword());

        if (userEntity == null) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }

        return new UserDtoResponse(userEntity);
    }
}
