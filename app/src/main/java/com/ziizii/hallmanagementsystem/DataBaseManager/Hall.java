package com.ziizii.hallmanagementsystem.DataBaseManager;

public class Hall
{
    private Slot[] slots = new Slot[7];
    private String hallName;

    public Hall(String hallName)
    {
        this.hallName = hallName;
    }

    public Slot[] getSlots()
    {
        return slots;
    }

    public void setSlots(Slot[] slots)
    {
        this.slots = slots;
    }

    public void setSlot(int i, Slot slot)
    {
        this.slots[i] = slot;
    }
}