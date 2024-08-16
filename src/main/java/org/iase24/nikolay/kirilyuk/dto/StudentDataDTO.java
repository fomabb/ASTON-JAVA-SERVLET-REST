package org.iase24.nikolay.kirilyuk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDataDTO {

    private Long id;
    private String name;
    private StatusUser status;
}
