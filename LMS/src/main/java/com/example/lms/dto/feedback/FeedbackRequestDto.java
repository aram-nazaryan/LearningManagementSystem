package com.example.lms.dto.feedback;

import lombok.Data;

import java.util.List;

@Data
public class FeedbackRequestDto {

    private String userUuid;

    private List<String> courseUuids;

}
