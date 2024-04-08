package com.kauan.GerenciamentoDeTarefas.services.task;

import com.kauan.GerenciamentoDeTarefas.dtos.task.TaskDto;
import com.kauan.GerenciamentoDeTarefas.dtos.task.TaskDtoResponse;
import com.kauan.GerenciamentoDeTarefas.entities.task.Status;
import com.kauan.GerenciamentoDeTarefas.entities.task.TaskEntity;
import com.kauan.GerenciamentoDeTarefas.entities.user.UserEntity;
import com.kauan.GerenciamentoDeTarefas.repositories.TaskRepository;
import com.kauan.GerenciamentoDeTarefas.repositories.UserRepository;
import com.kauan.GerenciamentoDeTarefas.services.exceptions.DatabaseException;
import com.kauan.GerenciamentoDeTarefas.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TaskDtoResponse createTask(Long userId, TaskDto taskDto) {
        try {
            TaskEntity taskEntity = new TaskEntity();

            taskEntity.setTitle(taskDto.getTitle());
            taskEntity.setDescription(taskDto.getDescription());
            taskEntity.setDueData(taskDto.getDueDate());
            taskEntity.setStatus(Status.PENDING);

            UserEntity userEntity = userRepository.getReferenceById(userId);

            taskEntity.setUser(userEntity);

            taskEntity = taskRepository.save(taskEntity);

            return new TaskDtoResponse(taskEntity);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    @Transactional(readOnly = true)
    public TaskDtoResponse readTask(Long userId, Long taskId) {
        try {
            TaskEntity taskEntity = taskRepository.findByUserIdAndId(userId, taskId);

            return new TaskDtoResponse(taskEntity);
        }catch (NullPointerException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional
    public TaskDtoResponse updateTask(Long userId, Long taskId, TaskDto taskDto) {
        try {
            TaskEntity taskEntity = taskRepository.findByUserIdAndId(userId, taskId);

            taskEntity.setTitle(taskDto.getTitle());
            taskEntity.setDescription(taskDto.getDescription());
            taskEntity.setDueData(taskDto.getDueDate());

            taskEntity = taskRepository.save(taskEntity);

            return new TaskDtoResponse(taskEntity);
        }catch (NullPointerException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteTask(Long userId, Long taskId) {
        TaskEntity taskEntity = taskRepository.findByUserIdAndId(userId, taskId);

        if (taskEntity == null) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        taskRepository.delete(taskEntity);
    }

    @Transactional(readOnly = true)
    public Page<TaskDtoResponse> listUserTasks(Long userId, Pageable pageable) {
        Page<TaskEntity> tasksEntities = taskRepository.findAllByUserId(userId, pageable);

        return tasksEntities.map(TaskDtoResponse::new);
    }
}