package org.generation.italy.model.data.implementations;

import org.generation.italy.model.data.abstractions.DepartmentRepository;
import org.generation.italy.model.data.exceptions.DataException;
import org.generation.italy.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.model.entities.Department;
import org.generation.italy.model.entities.Employee;;
import static org.generation.italy.model.Queries.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCDepartmentRepository implements DepartmentRepository {
    private Connection con;

    public JDBCDepartmentRepository(Connection con){
        this.con = con;
    }

    @Override
    public Department addDepartment(Department department) throws SQLException {
        try (
             PreparedStatement st = con.prepareStatement(ADD_DEPARTMENT_RETURNING_ID, Statement.RETURN_GENERATED_KEYS);
        ) {
            st.setString(1, department.getName());
            st.setString(2, department.getAddress());
            st.setInt(3, department.getMaxCapacity());
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                rs.next();
                long key = rs.getLong(1);
                department.setId(key);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public void deleteDepartmentById(long id) throws EntityNotFoundException, DataException {
        try(
            PreparedStatement st = con.prepareStatement(DELETE_DEPARTMENT_BY_ID);
        ){
            st.setLong(1, id);
            int numLines = st.executeUpdate();
            if(numLines != 1){
                throw new EntityNotFoundException("Non è stato trovato il dipartimento con id " + id);
            }
        } catch (SQLException e) {
            throw new DataException("Errore lettura dal database");
        }
    }

    @Override
    public Iterable<Department> getDepartmentsByName(String departmentName) throws SQLException {
        try(PreparedStatement ps = con.prepareStatement(GET_DEPARTMENTS_BY_NAME)){
            List<Department> departmentList = new ArrayList<>();
            ps.setString(1, "%" + departmentName + "%");
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    departmentList.add(databaseToDepartment(rs)); // ovviamente non ritorna i dipendenti
                }
            }
            return departmentList;
        }
    }

    private Department databaseToDepartment(ResultSet rs) throws SQLException {
        try{
            return new Department(
                    rs.getLong("department_id"),
                    rs.getString("department_name"),
                    rs.getString("department_address"),
                    rs.getInt("max_capacity")
            );
        } catch (SQLException e) {
            throw new SQLException("Errore lettura dal database");
        }
    }

    private Employee databaseToEmployee(ResultSet rs) throws SQLException{
        try{
            return new Employee(
                    rs.getLong("employee_id"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getDate("hiredate").toLocalDate(),
                    rs.getString("sex"),
                    rs.getLong("department_id")
            );
        } catch (SQLException e){
            throw new SQLException("Errore lettura dal database");
        }
    }

    /*
     *   c. Un metodo che mi da tutti i dipartimenti che contengono una data stringa nel nome,
     *       ma quest'ultimo deve anche caricare tutti gli impiegati che ci lavorano
     * 2a. fare i test per tutti i metodi che riusciamo a fare
     * Quindi la classe Dipartimento avrà un Set di impiegati e ogni Impiegato avrà un private Dipartimento
     * 3. (Opzionale) Creare due metodi per selezionare oggetti:
     *   a. uno prende una query parametrizzata, una lambda RawMapper che descriva come mappare una riga del ResultSet e l'oggeto da creare, e i var args relativi all'oggetto da creare
     *   b. un altro prende una query parametrizzata, una lambda StatementSetter che prende in input il PreparedStatement e ne setta i parametri necessari, infine la lambda RawMapper (senza i var args perché ci penserà StatementSetter)
     *
     * */
}
