package org.iase24.nikolay.kirilyuk.entity;

import jakarta.persistence.*;
import lombok.*;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "teacher")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Teacher extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusUser status;

    @OneToMany(mappedBy = "teacher",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private List<Student> students;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "course_id")
    private Course course;
}
