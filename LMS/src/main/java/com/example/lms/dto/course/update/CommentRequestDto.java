package com.example.lms.dto.course.update;

import lombok.Data;

@Data
public class CommentRequestDto extends SessionInfoDto {
    private String comment;
}
