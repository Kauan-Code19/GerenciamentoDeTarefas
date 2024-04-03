package com.kauan.GerenciamentoDeTarefas.services.user;

import com.kauan.GerenciamentoDeTarefas.dtos.user.UserDto;
import com.kauan.GerenciamentoDeTarefas.dtos.user.UserDtoResponse;
import com.kauan.GerenciamentoDeTarefas.entities.user.UserEntity;
import com.kauan.GerenciamentoDeTarefas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDtoResponse createUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();

        userEntity.setLogin(userDto.getLogin());
        userEntity.setPassword(userDto.getPassword());

        userEntity = userRepository.save(userEntity);

        return new UserDtoResponse(userEntity);
    }

    @Transactional(readOnly = true)
    public UserDtoResponse readUser(Long userId) {
        UserEntity userEntity = userRepository.getReferenceById(userId);

        return new UserDtoResponse(userEntity);
    }

    @Transactional
    public UserDtoResponse updateUser(Long userId, UserDto userDto) {
        UserEntity userEntity = userRepository.getReferenceById(userId);

        userEntity.setLogin(userDto.getLogin());
        userEntity.setPassword(userDto.getPassword());

        userEntity = userRepository.save(userEntity);

        return new UserDtoResponse(userEntity);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}