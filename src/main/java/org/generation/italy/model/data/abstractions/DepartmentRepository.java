package org.generation.italy.model.data.abstractions;

import org.generation.italy.model.data.exceptions.DataException;
import org.generation.italy.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.model.entities.Department;

import java.sql.SQLException;
import java.util.List;

public interface DepartmentRepository {
    public Department addDepartment(Department department) throws SQLException;
    public void deleteDepartmentById(long id) throws EntityNotFoundException, DataException;
    public Iterable<Department> getDepartmentsByName(String departmentName) throws SQLException;
}
