package com.example.appointment_schedule.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalTime;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartTimeBeforeEndTimeValidator.class)
@Documented
public @interface StartTimeBeforeEndTime {
    String message() default "Start time must be before end time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String start();
    String end();
    String min() default "9:59";
    String max() default "20:00";

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        StartTimeBeforeEndTime[] value();
    }
}

