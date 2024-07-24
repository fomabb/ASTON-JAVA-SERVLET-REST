package org.iase24.nikolay.kirilyuk.mapper;

import lombok.NoArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@NoArgsConstructor(access = PRIVATE)
public class CourseMapper implements Mapper<Course, CourseDataDTO> {

    private static final CourseMapper INSTANCE = new CourseMapper();

    public static CourseMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public CourseDataDTO map(Course object) {
        return CourseDataDTO.builder()
                .id(object.getId())
                .name(object.getName())
                .build();
    }
}
