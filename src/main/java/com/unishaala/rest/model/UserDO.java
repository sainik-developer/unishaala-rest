package com.unishaala.rest.model;

import com.unishaala.rest.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity(name = "users")
@NoArgsConstructor
public class UserDO {
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "uuid", unique = true)
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
    // only for student
    private ClassDO relatedClass;
    private String email;
    private LocalDate dob;
    private String fullName;
    // only for teacher
    @Column(name = "about_me")
    private String aboutMe;
}
