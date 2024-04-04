package com.kauan.GerenciamentoDeTarefas.services.task;

import com.kauan.GerenciamentoDeTarefas.dtos.task.TaskDto;
import com.kauan.GerenciamentoDeTarefas.dtos.task.TaskDtoResponse;
import com.kauan.GerenciamentoDeTarefas.entities.task.Status;
import com.kauan.GerenciamentoDeTarefas.entities.task.TaskEntity;
import com.kauan.GerenciamentoDeTarefas.entities.user.UserEntity;
import com.kauan.GerenciamentoDeTarefas.repositories.TaskRepository;
import com.kauan.GerenciamentoDeTarefas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setDueData(taskDto.getDueDate());
        taskEntity.setStatus(Status.PENDING);

        UserEntity userEntity = userRepository.getReferenceById(userId);

        taskEntity.setUser(userEntity);

        taskEntity = taskRepository.save(taskEntity);

        return new TaskDtoResponse(taskEntity);
    }

    @Transactional(readOnly = true)
    public TaskDtoResponse readTask(Long userId, Long taskId) {
        TaskEntity taskEntity = taskRepository.findByUserIdAndId(userId, taskId);

        return new TaskDtoResponse(taskEntity);
    }

    @Transactional
    public TaskDtoResponse updateTask(Long userId, Long taskId, TaskDto taskDto) {
        TaskEntity taskEntity = taskRepository.findByUserIdAndId(userId, taskId);

        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setDueData(taskDto.getDueDate());

        taskEntity = taskRepository.save(taskEntity);

        return new TaskDtoResponse(taskEntity);
    }
}