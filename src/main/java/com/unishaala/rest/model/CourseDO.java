package com.unishaala.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "courses")
public class CourseDO {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private UUID id;
    private String name;
    @OneToOne(optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherDO teacherDO;
    private String details;
    private String urlCourse;
}
