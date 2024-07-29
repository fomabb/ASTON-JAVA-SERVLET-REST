package org.iase24.nikolay.kirilyuk.model.out;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorRestOut {

    private final String url;
    private final HttpMethod method;
    private final HttpStatus status;
    private final String timestamp;
    private final String message;
}
