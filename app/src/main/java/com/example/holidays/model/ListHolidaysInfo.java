package com.example.holidays.model;

public class ListHolidaysInfo {
    public HolidaysInfo[] listHolidaysInfo;

    public ListHolidaysInfo(int size){
        listHolidaysInfo = new HolidaysInfo[size];
    }

    public void addHoliday(String holiday_name, String holiday_date, int id){
        HolidaysInfo holiday = new HolidaysInfo(holiday_name, holiday_date);
        listHolidaysInfo[id] = holiday;
    }
}
