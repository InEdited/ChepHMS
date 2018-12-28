package com.ziizii.hallmanagementsystem.HelperClasses;

public class CourseTime {
    private String day, hall, instructor;
    private int slot;

    public CourseTime(String day, String hall, String instructor, int slot)
    {
        this.day = day;
        this.hall = hall;
        this.instructor = instructor;
        this.slot = slot;
    }

    public String getDay() {
        return day;
    }

    public String getHall() {
        return hall;
    }

    public int getSlot() {
        return slot;
    }

    public String getInstructor() {
        return instructor;
    }
}
