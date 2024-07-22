package org.iase24.nikolay.kirilyuk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDataDTO {

    private Long id;
    private String name;
    private StatusUser status;
}
