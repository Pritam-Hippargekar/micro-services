package com.ayushman.WebFluxApi.dto;

import java.util.Objects;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@Data
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
public class Student {

    private int id;
    private String firstName;
    private String lastName;

    public Student() {
    }

    public Student(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && firstName.equals(student.firstName) && lastName.equals(student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
