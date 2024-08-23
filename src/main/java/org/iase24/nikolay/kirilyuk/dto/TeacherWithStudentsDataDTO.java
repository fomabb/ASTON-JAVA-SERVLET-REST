package org.iase24.nikolay.kirilyuk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherWithStudentsDataDTO {

    private Long id;
    private String name;
    private StatusUser status;
    List<StudentDataDTO> students;
}
