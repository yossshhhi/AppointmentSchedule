package com.example.appointment_schedule.services;

import com.example.appointment_schedule.entity.Day;
import com.example.appointment_schedule.repositories.DayRepository;
import com.example.appointment_schedule.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DayService {
    private final DayRepository dayRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public DayService(DayRepository dayRepository, DoctorRepository doctorRepository) {
        this.dayRepository = dayRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Day> findAllDays() {
        return dayRepository.findAll();
    }

    public List<Day> findOffDays() {
        List<Day> allDays = findAllDays();
        List<Day> offDays = new ArrayList<>();
        for (int i = 0; i < allDays.size(); i++) {
            Date date = new Date();
            if(allDays.get(i).getDate().before(new Date(date.getTime() - (1000 * 60 * 60 * 24)))) {
                dayRepository.delete(allDays.get(i));
            }
            if(allDays.get(i).getTime().isEmpty()) {
                offDays.add(allDays.get(i));
            }
        }
        return offDays;
    }

    public List<Day> findWordDays() {
        List<Day> allDays = findAllDays();
        List<Day> workDays = new ArrayList<>();
        for (int i = 0; i < allDays.size(); i++) {
            if(!allDays.get(i).getTime().isEmpty())
                workDays.add(allDays.get(i));
        }
        return workDays;
    }

    public Map<String, List<String>> mapDatesWithTime() {
        List<Day> workDays = findWordDays();
        Map<String, List<String>> datesWithTime = new HashMap<>();
        for (int i = 0; i < workDays.size(); i++) {
            Day day = workDays.get(i);
            List<String> times = new ArrayList<>();
            for (int j = 0; j < day.getTime().size(); j++) {
                times.add(day.getTime().get(j).getTime());
            }
            datesWithTime.put(day.getDate().toString(), times);
        }
        return datesWithTime;
    }

    public List<Date> listDisabledDays() {
        List<Date> disabledDays = new ArrayList<>();
        List<Day> offDays = findOffDays();
        for (int i = 0; i < offDays.size(); i++) {
            disabledDays.add(offDays.get(i).getDate());
        }
        return  disabledDays;
    }

    @Transactional
    public void insertDates(long id) {
        Date lastDateInDB = dayRepository.findMaxDateByDoctorId(id);
        Date installBeforeThisDate = getFistDayOfMonth(lastDateInDB);
        Date toSave = getNextDay(lastDateInDB);
        while (toSave.before(installBeforeThisDate)) {
            save(new Day(toSave, doctorRepository.findById(id).get()));
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


    public Day findOne(long id) {
        Optional<Day> day = dayRepository.findById(id);
        return day.orElse(null);
    }

    @Transactional
    public void save(Day day) {
        dayRepository.save(day);
    }

    @Transactional
    public void update(long id, Day day) {
        day.setId(id);
        dayRepository.save(day);
    }

    @Transactional
    public void delete(long id) {
        dayRepository.deleteById(id);
    }
}
