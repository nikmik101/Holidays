package com.example.holidays.model;

public class HolidaysInfo {
    int id;
    String holiday_name;
    String holiday_date;

    public HolidaysInfo (String name, String date){
        this.holiday_date = date;
        this.holiday_name = name;
    }

    public String getHoliday_date() {
        return holiday_date;
    }

    public String getHoliday_name() {
        return holiday_name;
    }
}

