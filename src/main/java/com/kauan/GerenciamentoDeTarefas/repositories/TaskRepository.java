package com.kauan.GerenciamentoDeTarefas.repositories;

import com.kauan.GerenciamentoDeTarefas.dtos.task.TaskStatusDto;
import com.kauan.GerenciamentoDeTarefas.entities.task.Status;
import com.kauan.GerenciamentoDeTarefas.entities.task.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    TaskEntity findByUserIdAndId(Long userId, Long taskId);
    Page<TaskEntity> findAllByUserId(Long userId, Pageable pageable);
    Page<TaskEntity> findAllByUserIdAndStatus(Long userId, Status status, Pageable pageable);
}