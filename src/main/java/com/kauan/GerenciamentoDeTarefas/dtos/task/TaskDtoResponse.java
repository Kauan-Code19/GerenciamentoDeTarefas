package com.kauan.GerenciamentoDeTarefas.dtos.task;

import com.kauan.GerenciamentoDeTarefas.entities.task.Status;
import com.kauan.GerenciamentoDeTarefas.entities.task.TaskEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class TaskDtoResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueData;
    private Status status;

    public TaskDtoResponse(TaskEntity taskEntity) {
        id = taskEntity.getId();
        title = taskEntity.getTitle();
        description = taskEntity.getDescription();
        dueData = taskEntity.getDueData();
        status = taskEntity.getStatus();
    }
}
