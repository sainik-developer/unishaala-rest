package com.unishaala.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "classes")
@AllArgsConstructor
public class Class {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private UUID id;
    @Column(name = "class_name")
    private String className;
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    @OneToMany
    private List<User> users;
}
