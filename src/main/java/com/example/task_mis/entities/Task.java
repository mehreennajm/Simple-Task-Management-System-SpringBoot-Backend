package com.example.task_mis.entities;

import com.example.task_mis.enums.TaskStatus;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Task {

    public Task(Long taskId, User userr) {
        this.taskId = taskId;
        this.userr = userr;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "create_date",nullable = false)
    private LocalDate createDate;

    @Column(name = "due_date",nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;


    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User userr;


}
