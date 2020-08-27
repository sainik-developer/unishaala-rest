package com.unishaala.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unishaala.rest.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "user_name")
    private String userName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "mobile_number")
    private String mobileNumber;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "user_type")
    private UserType userType;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "avatar_url")
    private String avatarUrl;
}
