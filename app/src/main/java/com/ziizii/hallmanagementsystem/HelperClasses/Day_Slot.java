package com.ziizii.hallmanagementsystem.HelperClasses;

public class Day_Slot {
    private String day;
    private int slot;

    public Day_Slot(String day, int slot)
    {
        this.day = day;
        this.slot = slot;
    }

    public String getDay() {
        return day;
    }

    public int getSlot() {
        return slot;
    }
}
