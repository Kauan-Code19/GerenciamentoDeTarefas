package com.kauan.GerenciamentoDeTarefas.services.user;

import com.kauan.GerenciamentoDeTarefas.dtos.user.UserDto;
import com.kauan.GerenciamentoDeTarefas.dtos.user.UserDtoResponse;
import com.kauan.GerenciamentoDeTarefas.entities.user.UserEntity;
import com.kauan.GerenciamentoDeTarefas.repositories.UserRepository;
import com.kauan.GerenciamentoDeTarefas.services.exceptions.DatabaseException;
import com.kauan.GerenciamentoDeTarefas.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDtoResponse createUser(UserDto userDto) {
        try {
            UserEntity userEntity = new UserEntity();

            userEntity.setName(userDto.getName());
            userEntity.setLogin(userDto.getLogin());
            userEntity.setPassword(userDto.getPassword());

            userEntity = userRepository.save(userEntity);

            return new UserDtoResponse(userEntity);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    @Transactional(readOnly = true)
    public UserDtoResponse readUser(Long userId) {
        try {
            UserEntity userEntity = userRepository.getReferenceById(userId);

            return new UserDtoResponse(userEntity);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional
    public UserDtoResponse updateUser(Long userId, UserDto userDto) {
        try {
            UserEntity userEntity = userRepository.getReferenceById(userId);

            userEntity.setName(userDto.getName());
            userEntity.setPassword(userDto.getPassword());

            if (!Objects.equals(userDto.getLogin(), userEntity.getLogin())) {
                throw new DatabaseException("Falha de integridade referencial");
            }

            userEntity.setLogin(userDto.getLogin());

            userEntity = userRepository.save(userEntity);

            return new UserDtoResponse(userEntity);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        try {
            userRepository.deleteById(userId);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}