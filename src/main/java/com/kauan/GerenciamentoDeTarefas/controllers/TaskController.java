package com.kauan.GerenciamentoDeTarefas.controllers;

import com.kauan.GerenciamentoDeTarefas.dtos.task.TaskDto;
import com.kauan.GerenciamentoDeTarefas.dtos.task.TaskDtoResponse;
import com.kauan.GerenciamentoDeTarefas.dtos.task.TaskStatusDto;
import com.kauan.GerenciamentoDeTarefas.services.task.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user/{userId}/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDtoResponse> createTask(@PathVariable Long userId,
                                                      @Valid @RequestBody TaskDto taskDto) {
        TaskDtoResponse taskDtoResponse = taskService.createTask(userId, taskDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(taskDtoResponse.getId()).toUri();

        return ResponseEntity.created(uri).body(taskDtoResponse);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDtoResponse> readTask(@PathVariable Long userId, @PathVariable Long taskId) {
        TaskDtoResponse taskDtoResponse = taskService.readTask(userId ,taskId);

        return ResponseEntity.ok().body(taskDtoResponse);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDtoResponse> updateTask(@PathVariable Long userId, @PathVariable Long taskId,
                                                      @Valid @RequestBody TaskDto taskDto) {
        TaskDtoResponse taskDtoResponse = taskService.updateTask(userId, taskId, taskDto);

        return ResponseEntity.ok(taskDtoResponse);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long userId, @PathVariable Long taskId) {
        taskService.deleteTask(userId, taskId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<TaskDtoResponse>> listUserTasks(@PathVariable Long userId, Pageable pageable) {
        Page<TaskDtoResponse> taskDtoResponses = taskService.listUserTasks(userId, pageable);

        return ResponseEntity.ok().body(taskDtoResponses);
    }

    @PutMapping("/{taskId}/update-status")
    public ResponseEntity<TaskDtoResponse> updateTask(@PathVariable Long userId, @PathVariable Long taskId,
                                                      @RequestBody TaskStatusDto taskStatusDto) {
        TaskDtoResponse taskDtoResponse = taskService.updateTaskStatus(userId, taskId, taskStatusDto);

        return ResponseEntity.ok(taskDtoResponse);
    }

    @GetMapping("/filter-by-status")
    public ResponseEntity<Page<TaskDtoResponse>> listTasksByStatus(@PathVariable Long userId,
                                                                  @RequestBody TaskStatusDto taskStatusDto,
                                                                   Pageable pageable)
    {
        Page<TaskDtoResponse> taskDtoResponses = taskService.listTasksByStatus(userId, taskStatusDto, pageable);

        return ResponseEntity.ok().body(taskDtoResponses);
    }
}