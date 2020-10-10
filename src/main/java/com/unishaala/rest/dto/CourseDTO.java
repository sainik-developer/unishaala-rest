package com.unishaala.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @NotBlank(message = "course name is must")
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private TeacherDTO teacher;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "teacher_id")
    @NotBlank(message = "teacher id is required")
    private UUID teacherId;
    @NotBlank(message = "course detail is must!")
    private String details;
    @JsonProperty("url_course_avatar")
    private String urlCourse;
}
