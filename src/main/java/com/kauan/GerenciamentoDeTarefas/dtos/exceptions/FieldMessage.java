package com.kauan.GerenciamentoDeTarefas.dtos.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FieldMessage {
    private String fieldName;
    private String message;
}