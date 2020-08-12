package com.unishaala.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "sessions")
@AllArgsConstructor
public class Session {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private UUID id;
    private LocalDateTime at;
    @OneToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @OneToOne(optional = false)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    @OneToOne(optional = false)
    @JoinColumn(name = "class_id", nullable = false)
    private Class aClass;
}
