package com.example.appointment_schedule.controllers;

import com.example.appointment_schedule.services.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class DoctorRestController {

    @Autowired
    private DayService dayService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getData(@PathVariable("id") long id) {

        Map<String, List<String>> datesWithTimeSlots = dayService.mapDatesWithTime(id);
        List<Date> disabledDates = dayService.listDisabledDates(id);

        Map<String, Object> response = new HashMap<>();
        response.put("datesWithTimeSlots", datesWithTimeSlots);
        response.put("disabledDates", disabledDates);

        return response;
    }
}
