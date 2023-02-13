package com.example.lms.service;

import com.example.lms.dto.feedback.FeedbackRequestDto;
import com.example.lms.dto.feedback.FeedbackResponseDto;

public interface EmailService {

    void sendMailWithAttachment(FeedbackResponseDto details);

    FeedbackResponseDto getFeedback(FeedbackRequestDto feedbackRequestDto);
}