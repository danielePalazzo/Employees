package org.generation.italy.model;

public class Queries {
    public static final String ADD_DEPARTMENT_RETURNING_ID = """
            INSERT INTO department(department_id, department_name, department_address_ max_capacity)
            VALUES(nextval('department_sequence'), ?, ?, ?)
            RETURNING department_id;
            """;

    public static final String DELETE_DEPARTMENT_BY_ID = """
            DELETE FROM department
            WHERE department_id = ?;
            """;

    public static final String GET_DEPARTMENTS_AND_EMPLOYEES_BY_DEPARTMENT_NAME = """
            SELECT d.department_id, d.department_name, d.department_address, e.employee_id,
            e.firstname, e.lastname, e.hiredate, e.sex
            FROM department AS d JOIN employee AS e
            USING (department_id)
            WHERE d.department_name LIKE '?%';
            """;

    public static String GET_DEPARTMENTS_BY_NAME = """
            SELECT *
            FROM department
            WHERE department_name LIKE '?%'
            """;
}
