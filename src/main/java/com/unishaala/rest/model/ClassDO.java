package com.unishaala.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "classes")
@NoArgsConstructor
public class ClassDO {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private UUID id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id", nullable = false)
    private SchoolDO school;
    @OneToMany(fetch = FetchType.LAZY)
    private List<UserDO> users;
}
