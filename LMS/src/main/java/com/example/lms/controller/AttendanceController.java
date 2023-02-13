package com.example.lms.controller;

import com.example.lms.dto.course.update.SessionInfoAttendanceDto;
import com.example.lms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PutMapping
    public ResponseEntity<String> setAttendance(@RequestBody SessionInfoAttendanceDto sessionInfoAttendanceDto) {
        log.info("setAttendance (PUT) with body - {}", sessionInfoAttendanceDto);
        attendanceService.setAttendance(sessionInfoAttendanceDto);
        return new ResponseEntity<>("Attendance updated successfully", HttpStatus.OK);
    }

}
