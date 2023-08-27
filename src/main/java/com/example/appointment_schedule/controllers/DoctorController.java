package com.example.appointment_schedule.controllers;

import com.example.appointment_schedule.entity.DayEntity;
import com.example.appointment_schedule.entity.DoctorEntity;
import com.example.appointment_schedule.entity.TimeEntity;
import com.example.appointment_schedule.services.AppointmentService;
import com.example.appointment_schedule.services.DayService;
import com.example.appointment_schedule.services.DoctorService;
import com.example.appointment_schedule.services.TimeService;
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
    private final TimeService timeService;
    private final AppointmentService appointmentService;

    @Autowired
    public DoctorController(DoctorService doctorService, DayService dayService,
                            TimeService timeService, AppointmentService appointmentService) {
        this.doctorService = doctorService;
        this.dayService = dayService;
        this.timeService = timeService;
        this.appointmentService = appointmentService;
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
        dayService.insertDates(doctor.getId());
        List<DayEntity> workDays = dayService.findWordDays(doctor.getId());
        List<TimeEntity> times = timeService.findTimeFromStartToEnd(doctor.getStart_time(), doctor.getEnd_time());
        for (DayEntity day : workDays) {
            dayService.setWorkTime(times, day);
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
        doctorService.update(id, doctor);

        List<DayEntity> workDays = dayService.findWordDays(id);
        List<TimeEntity> times = timeService.findTimeFromStartToEnd(doctor.getStart_time(), doctor.getEnd_time());
        for (DayEntity day : workDays) {
//            appointmentService.deleteByDay(day.getId());
            dayService.setWorkTime(times, day);
        }
        return "redirect:/doctors";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        doctorService.delete(id);
        return "redirect:/doctors";
    }

    public void deleteDays(List<DayEntity> days) {
        for (int i = 0; i < days.size(); i++) {
            dayService.delete(days.get(i).getId());
        }
    }
}
