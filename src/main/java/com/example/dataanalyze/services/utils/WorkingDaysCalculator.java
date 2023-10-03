package com.example.dataanalyze.services.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Утилитарный класс для расчета общего количества рабочих дней между двумя датами, включая начальную и конечную даты.
 */
public class WorkingDaysCalculator {
    public static int calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        int workingDays = 0;

        for (int i = 0; i < daysBetween; i++) {
            if (startDate.getDayOfWeek() != DayOfWeek.SATURDAY && startDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            startDate = startDate.plusDays(1);
        }

        return workingDays;
    }
}
