package com.codestates.todoapp.service;

import com.codestates.todoapp.dto.TodoDto;
import com.codestates.todoapp.entity.TodoEntity;
import com.codestates.todoapp.repository.TodoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Value("${server.service.url}")
    private String serviceUrl;

    public TodoDto create(TodoDto todoPostDto){

        if (ObjectUtils.isEmpty(todoPostDto.getOrder())) {
            todoPostDto.setOrder(0);
        }

        if (ObjectUtils.isEmpty(todoPostDto.getCompleted())) {
            todoPostDto.setCompleted(false);
        }
        TodoEntity todoEntity = TodoEntity.builder()
                .title(todoPostDto.getTitle())
                .order(todoPostDto.getOrder())
                .completed(todoPostDto.getCompleted())
                .build();
        TodoEntity savedEntity = this.todoRepository.save(todoEntity);
        return new TodoDto(savedEntity,serviceUrl);
    }

    public TodoDto read(long id) {
        TodoEntity todoEntity = this.findVerifiedTodoEntity(id);
        return new TodoDto(todoEntity,serviceUrl);
    }

    public List<TodoDto> readAll() {
        List<TodoEntity> readList = this.todoRepository.findAll();
        return readList
                .stream()
                .map(result -> new TodoDto(result,serviceUrl))
                .collect(Collectors.toList());
    }

    public TodoDto update(Long id, TodoDto todoDto) {
        TodoEntity findEntity = findVerifiedTodoEntity(id);

        Optional.ofNullable(todoDto.getTitle())
                .ifPresent(findEntity::setTitle);
        Optional.ofNullable(todoDto.getOrder())
                .ifPresent(findEntity::setOrder);
        Optional.ofNullable(todoDto.getCompleted())
                .ifPresent(findEntity::setCompleted);
        TodoEntity savedEntity = this.todoRepository.save(findEntity);
        return new TodoDto(savedEntity,serviceUrl);
    }

    public void delete(long id) {
        this.todoRepository.deleteById(id);
    }
    public void deleteAll() {
        this.todoRepository.deleteAll();
    }

    public TodoEntity findVerifiedTodoEntity(long id){
        Optional<TodoEntity> optionalTodoEntity = this.todoRepository.findById(id);
        return optionalTodoEntity.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}