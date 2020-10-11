package com.unishaala.rest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "sessions")
@NoArgsConstructor
public class SessionDO {
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "uuid", unique = true)
    private UUID id;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private CourseDO course;
    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = false)
    private ClassDO aClass;
    @OneToMany(fetch = FetchType.LAZY)
    private List<AttachmentDO> attachments;
    @Column(name = "duration_in_min")
    private int durationInMin;
    @Column(name = "note")
    private String note;
}
