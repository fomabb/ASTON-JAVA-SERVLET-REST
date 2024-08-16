package org.iase24.nikolay.kirilyuk.mapper;

import lombok.NoArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@NoArgsConstructor(access = PRIVATE)
public class TeacherMapper implements Mapper<Teacher, TeacherDataDTO> {

    private static final TeacherMapper INSTANCE = new TeacherMapper();

    public static TeacherMapper getInstance() {
        return INSTANCE;
    }


    @Override
    public TeacherDataDTO map(Teacher object) {
        return TeacherDataDTO.builder()
                .id(object.getId())
                .name(object.getName())
                .status(object.getStatus())
                .build();
    }
}
