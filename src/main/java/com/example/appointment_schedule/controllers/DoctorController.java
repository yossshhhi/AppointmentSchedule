package com.example.appointment_schedule.controllers;

import com.example.appointment_schedule.entity.DayEntity;
import com.example.appointment_schedule.entity.DoctorEntity;
import com.example.appointment_schedule.services.DayService;
import com.example.appointment_schedule.services.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final DayService dayService;

    @Autowired
    public DoctorController(DoctorService doctorService, DayService dayService) {
        this.doctorService = doctorService;
        this.dayService = dayService;
    }

    @GetMapping()
    public String index(Model model) {
        List<DoctorEntity> doctors = doctorService.findAll();
        model.addAttribute("doctors", doctors);
        return "doctors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        if (dayService.findAllDays(id).size() < 15) {
            dayService.insertDates(id);
        }
        model.addAttribute("doctor", doctorService.findOne(id));

        return "doctors/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("doctor") DoctorEntity doctor) {
        return "doctors/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("doctor") @Valid DoctorEntity doctor, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "doctors/new";
        doctorService.save(doctor);
        if (dayService.findAllDays(doctor.getId()).size() < 15) {
            dayService.insertDates(doctor.getId());
        }
        List<DayEntity> workDays = dayService.findWordDays(doctor.getId());
        List<DayEntity> daysWithoutHolidays = dayService.filterHolidays(workDays);
        for (DayEntity day : daysWithoutHolidays) {
            dayService.setWorkTime(doctor.getStart(), doctor.getEnd(), day);
        }
        return "redirect:/doctors";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("doctor", doctorService.findOne(id));
        return "doctors/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("doctor") @Valid DoctorEntity doctor,
                         BindingResult bindingResult, @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "doctors/edit";
        List<DayEntity> workDays = doctorService.findOne(id).getDays();
        List<DayEntity> daysWithoutHolidays = dayService.filterHolidays(workDays);
        for (DayEntity day : daysWithoutHolidays) {
            dayService.setWorkTime(doctor.getStart(), doctor.getEnd(), day);
            dayService.update(day.getId(), day);
        }
        doctorService.update(id, doctor);
        return "redirect:/doctors";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        doctorService.delete(id);
        return "redirect:/doctors";
    }
}
