package org.iase24.nikolay.kirilyuk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "teacher")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Teacher extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusUser status;

    @JsonBackReference("teacher-student")
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}
