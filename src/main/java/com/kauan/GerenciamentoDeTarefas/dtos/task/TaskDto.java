package com.kauan.GerenciamentoDeTarefas.dtos.task;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class TaskDto {
    @NotBlank(message = "O título não pode estar em branco")
    private String title;

    private String description;

    @NotNull(message = "A data de vencimento não pode ser nula")
    @FutureOrPresent(message = "A data de vencimento deve ser presente ou futura")
    private LocalDate dueDate;
}