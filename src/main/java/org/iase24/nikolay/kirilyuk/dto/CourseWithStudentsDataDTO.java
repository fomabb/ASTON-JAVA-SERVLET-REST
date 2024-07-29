package org.iase24.nikolay.kirilyuk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseWithStudentsDataDTO {

    private Long id;
    private String name;
    private List<StudentDataDTO> students;
}
