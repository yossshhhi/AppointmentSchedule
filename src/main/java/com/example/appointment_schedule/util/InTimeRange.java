package com.example.appointment_schedule.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InTimeRangeValidator.class)
@Documented
public @interface InTimeRange {
    String message() default "Time must be between 10:00 and 20:00";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String min() default "09:59";
    String max() default "20:00";
}
