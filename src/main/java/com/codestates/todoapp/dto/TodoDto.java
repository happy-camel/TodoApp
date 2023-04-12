package com.codestates.todoapp.dto;

import com.codestates.todoapp.entity.TodoEntity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private Long id;
    private String title;
    private Integer order;
    private Boolean completed;
    private String url;

    public TodoDto(TodoEntity todoEntity, String serviceUrl) {
        this.id = todoEntity.getId();
        this.title = todoEntity.getTitle();
        this.order = todoEntity.getOrder();
        this.completed = todoEntity.getCompleted();
        this.url = serviceUrl + this.id;
    }

}
