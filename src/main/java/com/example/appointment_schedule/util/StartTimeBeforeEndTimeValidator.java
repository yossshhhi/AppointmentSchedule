//package com.example.appointment_schedule.util;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import org.springframework.beans.BeanWrapperImpl;
//
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
//public class StartTimeBeforeEndTimeValidator implements ConstraintValidator<StartTimeBeforeEndTime, Object> {
//
//    private String startFieldName;
//    private String endFieldName;
//
//    @Override
//    public void initialize(StartTimeBeforeEndTime constraintAnnotation) {
//        this.startFieldName = constraintAnnotation.start();
//        this.endFieldName = constraintAnnotation.end();
//    }
//
//    @Override
//    public boolean isValid(Object value, ConstraintValidatorContext context) {
//        String startTime = (String) new BeanWrapperImpl(value).getPropertyValue(startFieldName);
//        String endTime = (String) new BeanWrapperImpl(value).getPropertyValue(endFieldName);
//
//        // Implement the logic to compare start and end time
//        return compareTime(startTime, endTime) < 0;
//    }
//
//    private int compareTime(String time1, String time2) {
//        // Implement your custom logic to compare two time strings here.
//        // For example, you can convert them to LocalTime objects and use the compareTo method.
//        // In this case, since the time format is "HH:mm", you can use the following code:
//        LocalTime startTime = LocalTime.parse(time1, DateTimeFormatter.ofPattern("HH:mm"));
//        LocalTime endTime = LocalTime.parse(time2, DateTimeFormatter.ofPattern("HH:mm"));
//        return startTime.compareTo(endTime);
//    }
//}
//
