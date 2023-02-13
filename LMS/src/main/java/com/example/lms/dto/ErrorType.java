package com.example.lms.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    USER_ALREADY_REGISTERED("user_already_registered", HttpStatus.UNPROCESSABLE_ENTITY),
    INVALID_BODY("missing_body_parameters", HttpStatus.BAD_REQUEST),
    INCORRECT_LOGIN_DETAILS("incorrect_login_or_password", HttpStatus.NOT_FOUND),
    COURSE_NOT_FOUND("specified_course_does_not_exist", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("specified_user_does_not_exist", HttpStatus.NOT_FOUND),
    INCORRECT_PATH_VARIABLE("incorrect_path_variable", HttpStatus.UNPROCESSABLE_ENTITY);

    private final String id;
    private final HttpStatus httpStatus;

    ErrorType(String id, HttpStatus httpStatus) {
        this.id = id;
        this.httpStatus = httpStatus;
    }

    public ErrorDto errorDto() {
        return new ErrorDto(getId(), name(), getHttpStatus());
    }
}
