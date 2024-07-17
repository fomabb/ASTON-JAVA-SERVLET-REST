package org.iase24.nikolay.kirilyuk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    private Long id;
    private String name;
    private StatusUser status;
    private Course course;
    private List<Student> students;
}
