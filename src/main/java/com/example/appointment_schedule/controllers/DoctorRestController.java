package com.example.appointment_schedule.controllers;

import com.example.appointment_schedule.entity.Day;
import com.example.appointment_schedule.entity.Doctor;
import com.example.appointment_schedule.services.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctors")
public class DoctorRestController {

    @Autowired
    private DayService dayService;

    @GetMapping(path = "/show", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getData() {

        Map<String, List<String>> datesWithTimeSlots = dayService.mapDatesWithTime();
        List<Date> disabledDates = dayService.listDisabledDays();

        Map<String, Object> response = new HashMap<>();
        response.put("datesWithTimeSlots", datesWithTimeSlots);
        response.put("disabledDates", disabledDates);

        return response;
    }
}
