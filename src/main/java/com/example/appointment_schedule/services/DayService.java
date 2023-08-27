package com.example.appointment_schedule.services;

import com.example.appointment_schedule.entity.AppointmentEntity;
import com.example.appointment_schedule.entity.DayEntity;
import com.example.appointment_schedule.entity.TimeEntity;
import com.example.appointment_schedule.repositories.DayRepository;
import com.example.appointment_schedule.repositories.DoctorRepository;
import com.example.appointment_schedule.repositories.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DayService {
    private final DayRepository dayRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentService appointmentService;

    @Autowired
    public DayService(DayRepository dayRepository, DoctorRepository doctorRepository, AppointmentService appointment) {
        this.dayRepository = dayRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentService = appointment;
    }

    public List<DayEntity> findAllDays(long doctor_id) {
        return dayRepository.findDayEntitiesByDoctorId(doctor_id);
    }

    public List<DayEntity> findOffDays(long doctor_id) {
        List<DayEntity> offDays = dayRepository.findDayEntitiesByDoctorIdAndWorkDay(doctor_id, false);
        deletePastDays(offDays);
        return offDays;
    }

    public List<DayEntity> findWordDays(long doctor_id) {
        List<DayEntity> workDays = dayRepository.findDayEntitiesByDoctorIdAndWorkDay(doctor_id, true);
        deletePastDays(workDays);
        return workDays;
    }

    public void deletePastDays(List<DayEntity> days) {
        for (int i = 0; i < days.size(); i++) {
            Date date = new Date();
            if(days.get(i).getDate().before(new Date(date.getTime() - (1000 * 60 * 60 * 24)))) {
                dayRepository.delete(days.get(i));
            }
        }
    }

    public Map<String, List<String>> mapDatesWithTime(long doctor_id) {
        List<DayEntity> workDays = appointmentService.findDays(doctor_id);
        Map<String, List<String>> datesWithTime = new HashMap<>();
        for (int i = 0; i < workDays.size(); i++) {
            DayEntity day = workDays.get(i);
            List<TimeEntity> times = appointmentService.findTimes(day.getId());
            List<String> stringTime = new ArrayList<>();
            for (int j = 0; j < times.size(); j++) {
                stringTime.add(times.get(j).getAppointment().format(DateTimeFormatter.ofPattern("hh:mm a")));
            }
            datesWithTime.put(day.getDate().toString(), stringTime);
        }
        return datesWithTime;
    }

    public List<Date> listDisabledDates(long doctor_id) {
        List<Date> disabledDates = new ArrayList<>();
        List<DayEntity> offDays = findOffDays(doctor_id);
        for (int i = 0; i < offDays.size(); i++) {
            disabledDates.add(offDays.get(i).getDate());
        }
        return  disabledDates;
    }

    public boolean isItWorkday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY &&
                calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY;
    }

    @Transactional
    public void setWorkTime(List<TimeEntity> times, DayEntity day) {
        for (TimeEntity time : times) {
            appointmentService.save(new AppointmentEntity(time, day, day.getDoctor()));
        }
    }

    @Transactional
    public void insertDates(long id) {
        Date date = new Date();
        Date lastDateInDB = dayRepository.findMaxDateByDoctorId(id);
        if (lastDateInDB == null) {
            lastDateInDB = new Date(date.getTime() - (1000 * 60 * 60 * 24));
        }
        Date installBeforeThisDate = getFistDayOfMonth(lastDateInDB);
        Date toSave = getNextDay(lastDateInDB);
        while (toSave.before(installBeforeThisDate)) {
            save(new DayEntity(toSave, doctorRepository.findById(id).get(), isItWorkday(toSave)));
            toSave = getNextDay(toSave);
        }
    }

    public static Date getFistDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        calendar.set(year, month + 2, 1);

        return calendar.getTime();
    }

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTime();
    }


    public DayEntity findOne(long id) {
        Optional<DayEntity> day = dayRepository.findById(id);
        return day.orElse(null);
    }

    @Transactional
    public void save(DayEntity day) {
        dayRepository.save(day);
    }

    @Transactional
    public void update(long id, DayEntity day) {
        day.setId(id);
        dayRepository.save(day);
    }

    @Transactional
    public void delete(long id) {
        dayRepository.deleteById(id);
    }
}
