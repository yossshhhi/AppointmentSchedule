package com.example.appointment_schedule.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InTimeRangeValidator implements ConstraintValidator<InTimeRange, LocalTime> {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_TIME;
    private InTimeRange constraintAnnotation;

    @Override
    public void initialize(InTimeRange constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
        try {
            LocalTime minTime = LocalTime.parse(constraintAnnotation.min(), timeFormatter);
            LocalTime maxTime = LocalTime.parse(constraintAnnotation.max(), timeFormatter);
            return value == null || (value.isAfter(minTime) && value.isBefore(maxTime));
        } catch (DateTimeParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
