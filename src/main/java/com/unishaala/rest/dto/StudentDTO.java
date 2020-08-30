package com.unishaala.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unishaala.rest.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @NotBlank(message = "student name can't be null")
    @JsonProperty(value = "user_name")
    private String userName;
    @NotBlank(message = "student mobile number can't be null")
    @JsonProperty(value = "mobile_number")
    private String mobileNumber;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY,value = "user_type")
    private UserType userType;
    @JsonProperty(value = "avatar_url", access = JsonProperty.Access.READ_ONLY)
    private String avatarUrl;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ClassDTO aClass;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "class_id")
    private UUID classId;
    @Email
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Student date of birth is mandatory")
    private LocalDate dob;
    @NotBlank(message = "student full name can't be empty")
    @JsonProperty(value = "full_name")
    private String fullName;
}
