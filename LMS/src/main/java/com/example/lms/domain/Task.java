package com.example.lms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Table(name = "TASKS")
@Entity
@Getter
@Setter
public class Task {
    @Id
    @SequenceGenerator(
            name = "tasks_sequence",
            sequenceName = "task_sequence_seq",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tasks_sequence"
    )
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "number", nullable = false)
    private Long number;

    @OneToMany(mappedBy = "task")
    private List<UserTask> userTasks;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

}
