package com.example.appointment_schedule.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StartTimeBeforeEndTimeValidator implements ConstraintValidator<StartTimeBeforeEndTime, LocalTime> {
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime minTime;
    private LocalTime maxTime;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_TIME;
    private StartTimeBeforeEndTime constraintAnnotation;
    @Override
    public void initialize(StartTimeBeforeEndTime constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
        this.startTime = LocalTime.parse(constraintAnnotation.start(), timeFormatter);
        this.endTime = LocalTime.parse(constraintAnnotation.end(), timeFormatter);
    }

    @Override
    public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
        try {
            this.timeFormatter.parse(value.toString());
        } catch (DateTimeParseException e) {
            return false;
        }

        try {
            this.minTime = LocalTime.parse(constraintAnnotation.min(), timeFormatter);
            this.maxTime = LocalTime.parse(constraintAnnotation.max(), timeFormatter);
            if( value == null || (!value.isAfter(minTime) && !value.isBefore(maxTime)))
                return false;
        } catch (DateTimeParseException ex) {
            throw new RuntimeException(ex);
        }

        return startTime.isBefore(endTime);
    }

//    private int compareTime(String time1, String time2) {
//
//        LocalTime startTime = LocalTime.parse(time1, DateTimeFormatter.ofPattern("HH:mm"));
//        LocalTime endTime = LocalTime.parse(time2, DateTimeFormatter.ofPattern("HH:mm"));
//        return startTime.compareTo(endTime);
//    }
}

