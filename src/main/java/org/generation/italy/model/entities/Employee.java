package org.generation.italy.model.entities;

import java.time.LocalDate;

public class Employee {
    private long id;
    private String firstname;
    private String lastname;
    private LocalDate hireDate;
    private Sex sex;
    private long departmentId;

    public Employee(long id, String firstname, String lastname, LocalDate hireDate, String sex, long departmentId) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.hireDate = hireDate;
        this.sex = sex;
        this.departmentId = departmentId;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Sex getSex() {
        return sex;
    }

    public long getDepartmentId() {
        return departmentId;
    }
}
