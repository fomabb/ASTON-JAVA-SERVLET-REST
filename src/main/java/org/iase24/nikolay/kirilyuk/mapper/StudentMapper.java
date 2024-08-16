package org.iase24.nikolay.kirilyuk.mapper;

import lombok.NoArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@NoArgsConstructor(access = PRIVATE)
public class StudentMapper implements Mapper<Student, StudentDataDTO> {

    private static final StudentMapper INSTANCE = new StudentMapper();

    public static StudentMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public StudentDataDTO map(Student object) {
        return StudentDataDTO.builder()
                .id(object.getId())
                .name(object.getName())
                .status(object.getStatus())
                .build();
    }
}
