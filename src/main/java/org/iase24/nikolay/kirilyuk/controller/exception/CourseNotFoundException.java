package org.iase24.nikolay.kirilyuk.controller.exception;

public class CourseNotFoundException extends Throwable {
    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

