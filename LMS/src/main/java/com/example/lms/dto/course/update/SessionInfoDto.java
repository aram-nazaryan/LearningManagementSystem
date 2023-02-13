package com.example.lms.dto.course.update;

import lombok.Data;

@Data
public abstract class SessionInfoDto {

    private String courseUuid;

    private String userUuid;

    private Long sessionNumber;


}
