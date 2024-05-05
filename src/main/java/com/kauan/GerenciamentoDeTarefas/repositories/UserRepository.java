package com.kauan.GerenciamentoDeTarefas.repositories;

import com.kauan.GerenciamentoDeTarefas.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByLoginAndPassword(String login, String password);
}