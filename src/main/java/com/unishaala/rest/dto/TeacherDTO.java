package com.unishaala.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unishaala.rest.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @NotEmpty(message = "teacher should have a non empty first name")
    @JsonProperty(value = "first_name")
    private String firstName;
    @NotEmpty(message = "teacher should have a non empty last name")
    @JsonProperty(value = "last_name")
    private String lastName;
    @NotEmpty(message = "teacher should have a Valid mobile number")
    @JsonProperty(value = "mobile_number")
    private String mobileNumber;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "user_type")
    private UserType userType;
}
