package com.ziizii.hallmanagementsystem.HelperClasses;

public class RegisteredCourse {
    private int year;
    private String course_num, semester, grade;

    public RegisteredCourse(String course_num, int year, String semester, String grade)
    {
        this.course_num = course_num;
        this.year = year;
        this.semester = semester;
        this.grade = grade;
    }

    public String getCourse_num() {
        return course_num;
    }

    public int getYear() {
        return year;
    }

    public String getGrade() {
        return grade;
    }

    public String getSemester() {
        return semester;
    }
}
