package org.iase24.nikolay.kirilyuk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentsCourses {
    private Integer student_id;
    private Integer courses_id;
}
