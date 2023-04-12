package com.codestates.todoapp.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(name = "todoOrder", nullable = false)
    private Integer order;
    @Column(nullable = false)
    private Boolean completed;
}
