package com.example.appointment_schedule.controllers;

import com.example.appointment_schedule.entity.Doctor;
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
        List<Doctor> doctors = doctorService.findAll();
        model.addAttribute("doctors", doctors);
        return "doctors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        if (dayService.findAllDays().size() < 15) {
            dayService.insertDates(id);
        }
        if (dayService.findWordDays().size() < 15) {

        }
        model.addAttribute("doctor", doctorService.findOne(id));

        return "doctors/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("doctor") Doctor doctor) {
        return "doctors/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("doctor") @Valid Doctor doctor, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "doctors/new";

        doctorService.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("doctor", doctorService.findOne(id));
        return "doctors/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("doctor") @Valid Doctor doctor,
                         BindingResult bindingResult, @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "doctors/edit";
        doctorService.update(id, doctor);
        return "redirect:/doctors";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        doctorService.delete(id);
        return "redirect:/doctors";
    }
}
