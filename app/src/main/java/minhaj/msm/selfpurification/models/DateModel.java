package minhaj.msm.selfpurification.models;

import java.util.Calendar;

public class DateModel {

    public DateModel(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public int getId(){
        return (year * 10000) + ((month+1) * 100) + day;
    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate(){
        Calendar c=Calendar.getInstance();
        c.set(year,month,day);
        int dayOfw=c.get(Calendar.DAY_OF_WEEK);
        String[] days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

        String date = days[dayOfw-1]+" "+ day+ "/" + (month + 1)  + "/" + year;
        return date;
    }
    private String date;
    private int year;
    private int month;
    private int day;
}
