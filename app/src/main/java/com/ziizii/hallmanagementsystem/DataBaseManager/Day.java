package com.ziizii.hallmanagementsystem.DataBaseManager;


public class Day
{
    private Hall[] halls = new Hall[28];
    private String dayName;

    public Day(String dayName)
    {
        this.dayName = dayName;
    }

    public Day(){}

    public Hall[] getHalls()
    {
        return halls;
    }

    public void setDayName(String dayName)
    {
        this.dayName = dayName;
    }

    public String getDayName() { return this.dayName; }


    public void setHalls(Hall[] halls)
    {
        this.halls = halls;
    }

    public void setHall(int i, Hall hall)
    {
        this.halls[i] = hall;
    }
}
