package com.example.lms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "ATTENDANCE")
@Entity
public class Attendance {
    @Id
    @SequenceGenerator(
            name = "attendance_sequence",
            sequenceName = "attendance_sequence_seq",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "attendance_sequence"
    )
    private Long id;

    @Column(name = "present")
    private Boolean attendedStatus;

    @Column(name = "sessionNumber")
    private Integer sessionNumber;
    @ManyToOne
    @JoinColumn(name = "User_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
