package com.unishaala.rest.model;

import com.unishaala.rest.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity(name = "user")
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private UUID id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;
    @Column(name = "user_type")
    private UserType userType;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = true)
    private Class relatedClass;
}
