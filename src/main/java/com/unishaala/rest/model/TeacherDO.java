package com.unishaala.rest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "teachers")
public class TeacherDO {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private UUID id;
    private String name;
    private String surName;
    private String description;
    private String avatarUrl;
}
