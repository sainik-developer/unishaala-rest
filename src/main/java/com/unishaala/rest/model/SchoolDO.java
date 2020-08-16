package com.unishaala.rest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "schools")
@NoArgsConstructor
public class SchoolDO {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private UUID id;
    private String name;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    private String address;
    @OneToMany
    private List<ClassDO> classDOS;
}
