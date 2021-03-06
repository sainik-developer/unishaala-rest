package com.unishaala.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unishaala.rest.validator.ValidUuid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @JsonProperty(value = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm Z", timezone = "Asia/Kolkata")
    private LocalDateTime startTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "course_id")
    @ValidUuid(message = "course id is required!")
    private UUID courseId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CourseDTO course;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "class_id")
    @ValidUuid(message = "class id is required!")
    private UUID classId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ClassDTO aClass;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<AttachmentDTO> attachments;
    @JsonProperty(value = "duration_in_min")
    @Min(10)
    @Max(120)
    private int durationInMin;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "is_repeat")
    private boolean isRepeat = false;
    @JsonProperty(value = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd Z", timezone = "Asia/Kolkata")
    private LocalDate endDate;
    @JsonProperty(value = "all_or_working")
    private boolean isAllDaysOrWorkingDays = false;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "braincert_url")
    private String braincertUrl;
    @Column(name = "note")
    private String note;
}
