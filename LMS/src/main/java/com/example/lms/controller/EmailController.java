package com.example.lms.controller;

import com.example.lms.dto.feedback.FeedbackRequestDto;
import com.example.lms.dto.feedback.FeedbackResponseDto;
import com.example.lms.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/sendMailWithAttachment")
    public void sendMailWithAttachment(@RequestBody FeedbackResponseDto details)
    {
        emailService.sendMailWithAttachment(details);
    }

    @PostMapping("/feedback")
    public ResponseEntity<FeedbackResponseDto> getFeedback(@RequestBody FeedbackRequestDto feedbackRequestDto) {
        FeedbackResponseDto feedback = emailService.getFeedback(feedbackRequestDto);
        return ResponseEntity.ok(feedback);
    }

}
