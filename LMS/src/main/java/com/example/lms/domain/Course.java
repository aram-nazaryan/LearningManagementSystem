package com.example.lms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "COURSES")
public class Course {
    @Id
    @SequenceGenerator(
            name = "courses_sequence",
            sequenceName = "courses_sequence_seq",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "courses_sequence"
    )
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid = UUID.randomUUID().toString();
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "number_of_sessions", nullable = false)
    private Integer numberOfSessions;

    @Column(name = "start_date", nullable = false)
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDate endDate;

    @ManyToMany
    @JoinTable(
            name = "user_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;
}
