package com.unishaala.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@Entity(name = "braincerts")
@AllArgsConstructor
public class BraincertDO {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private UUID id;
    @Column(name = "is_teacher")
    private boolean isTeacher;
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserDO user;
    @OneToOne(optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private SessionDO session;
    private String url;
}
