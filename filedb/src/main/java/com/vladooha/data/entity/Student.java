package com.vladooha.data.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Student {
    private String surname;
    private String name;
    private String thirdname;
    private boolean sex;
    private Date birth;
    private int course;
    private String subject;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThirdname() {
        return thirdname;
    }

    public void setThirdname(String thirdname) {
        this.thirdname = thirdname;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public int hashCode() {
        double hashCode =
                Math.pow(birth.hashCode(), 2) +
                Math.pow(surname.length(), 2) +
                Math.pow(name.length(), 2) +
                Math.pow(thirdname.length(), 2);

        return (int) hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Student)) {
            return false;
        }

        Student student = (Student) obj;

        if (!student.name.equals(name) ||
                !student.surname.equals(surname) ||
                !student.thirdname.equals(thirdname) ||
                !student.birth.equals(birth)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(surname);
        stringBuilder.append(" ");
        stringBuilder.append(name);
        stringBuilder.append(" ");
        stringBuilder.append(thirdname);
        stringBuilder.append(", пол: ");
        stringBuilder.append(sex ? "М" : "Ж");
        stringBuilder.append(", дата рождения: ");
        stringBuilder.append(
                String.format(
                        "%02d.%02d.19%02d",
                        birth.getDay(),
                        birth.getMonth(),
                        birth.getYear()));
        stringBuilder.append(", ");
        stringBuilder.append(course + " курс");
        stringBuilder.append(", изучаемый предмет: ");
        stringBuilder.append(subject);

        return stringBuilder.toString();
    }
}
