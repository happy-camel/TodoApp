package com.codestates.todoapp.controller;

import com.codestates.todoapp.dto.TodoDto;
import com.codestates.todoapp.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoDto> newTodo(@RequestBody TodoDto todoPostDto){
//        return new ResponseEntity<>(todoService.create(todoPostDto), HttpStatus.CREATED);
        if (ObjectUtils.isEmpty(todoPostDto.getTitle())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.todoService.create(todoPostDto));
    }

    @PatchMapping("{id}")
    public  ResponseEntity<TodoDto> retouch(
            @PathVariable("id") long id,
            @RequestBody TodoDto todoPatchDto){
        TodoDto responseDto = this.todoService.update(id, todoPatchDto);
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoDto> check(@PathVariable("id") long id) {
//        return new ResponseEntity<>(todoService.read(id), HttpStatus.OK);
        return ResponseEntity.ok(this.todoService.read(id));
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> checkAll() {
//        return new ResponseEntity<>(todoService.readAll(), HttpStatus.OK);
        return ResponseEntity.ok(this.todoService.readAll());
    }

    @DeleteMapping
    public ResponseEntity<TodoDto> deleteAll() {
        this.todoService.deleteAll();
//        return new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        this.todoService.delete(id);
    }

}
