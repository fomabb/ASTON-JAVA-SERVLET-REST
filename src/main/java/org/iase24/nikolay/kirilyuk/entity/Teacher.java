package org.iase24.nikolay.kirilyuk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;

import javax.persistence.*;
import java.util.List;

//@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "teacher")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusUser status;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}
