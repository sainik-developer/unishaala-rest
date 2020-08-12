package com.unishaala.rest.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "classes")
public class Class {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private UUID id;
    private String className;
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    @OneToMany
    private List<User> users;
}
