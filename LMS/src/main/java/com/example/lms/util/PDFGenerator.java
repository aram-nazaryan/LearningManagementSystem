package com.example.lms.util;

import com.example.lms.dto.feedback.CourseFeedbackDto;
import com.example.lms.dto.feedback.FeedbackResponseDto;
import com.example.lms.dto.feedback.TaskFeedbackDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class PDFGenerator {

    public void createPDF(FeedbackResponseDto feedbackResponseDto) {
        Document document = new Document();

        try {
            String firstNameLastName =
                    feedbackResponseDto.getUserFirstName() + " " + feedbackResponseDto.getUserLastName();

            PdfWriter.getInstance(document, new FileOutputStream("./feedbackDir/" + firstNameLastName + ".pdf"));
            document.open();

            //Add Logo
            Image logo = Image.getInstance("./src/main/resources/sourcemind-logo.png");
            logo.scaleToFit(120f, 120f);
            logo.setAlignment(Element.ALIGN_LEFT);
            document.add(logo);


            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 26);
            Paragraph title = new Paragraph("Student Feedback", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Add user information
            Font userFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
            Paragraph userTitle = new Paragraph("Student Information", userFont);
            userTitle.setAlignment(Element.ALIGN_LEFT);
            document.add(userTitle);
            document.add(new Paragraph("\n"));

            Font userInfoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Student Name: " + firstNameLastName, userInfoFont));
            document.add(new Paragraph("User Email: " + feedbackResponseDto.getUserEmail(), userInfoFont));
            document.add(new Paragraph("Attendance Percentage: " + feedbackResponseDto.getAttendancePercentage() + "%", userInfoFont));
            document.add(new Paragraph("\n"));

            // Add course information
            Font courseFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
            Paragraph courseTitle = new Paragraph("Courses Feedback", courseFont);
            courseTitle.setAlignment(Element.ALIGN_LEFT);
            document.add(courseTitle);
            document.add(new Paragraph("\n"));

            for (CourseFeedbackDto course : feedbackResponseDto.getCourseFeedbackDtos()) {
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("Course: " + course.getName(), userInfoFont));

                // Add task information
                List<TaskFeedbackDto> taskFeedbackDtos = course.getTaskFeedbackDtos();
                if (!taskFeedbackDtos.isEmpty()) {
                    PdfPTable table = new PdfPTable(5);
                    table.setWidthPercentage(100);
                    table.setSpacingBefore(10f);
                    table.setSpacingAfter(10f);

                    float[] columnWidths = {2f, 3f, 5f, 1f, 2f};
                    table.setWidths(columnWidths);

                    PdfPCell cell1 = new PdfPCell(new Paragraph("Number"));
                    cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell1.setPadding(5);
                    PdfPCell cell2 = new PdfPCell(new Paragraph("Type"));
                    cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell1.setPadding(5);
                    PdfPCell cell3 = new PdfPCell(new Paragraph("Comment"));
                    cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell1.setPadding(5);
                    PdfPCell cell4 = new PdfPCell(new Paragraph("Grade"));
                    cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell1.setPadding(5);
                    PdfPCell cell5 = new PdfPCell(new Paragraph("Result"));
                    cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell1.setPadding(5);
                    table.addCell(cell1);
                    table.addCell(cell2);
                    table.addCell(cell3);
                    table.addCell(cell4);
                    table.addCell(cell5);

                    for (TaskFeedbackDto task : taskFeedbackDtos) {
                        table.addCell(String.valueOf(task.getNumber()));
                        table.addCell(String.valueOf(task.getType()));
                        table.addCell(String.valueOf(task.getComment()));
                        table.addCell(String.valueOf(task.getGrade()));
                        table.addCell(task.getPassed().equals("true") ? "Pass" : "Fail");
                    }
                    document.add(table);
                    document.add(new LineSeparator());
                }
            }

            // Add date to the bottom left corner
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
            String formattedDate = date.format(formatter);
            Paragraph dateParagraph = new Paragraph(formattedDate);
            dateParagraph.setAlignment(Element.ALIGN_LEFT);
            document.add(dateParagraph);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
