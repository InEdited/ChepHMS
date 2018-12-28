package com.ziizii.hallmanagementsystem.HelperClasses;

public class Student {
    private String name, id, grade;

    public Student(String name, String id, String grade)
    {
        this.name = name;
        this.grade = grade;
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
