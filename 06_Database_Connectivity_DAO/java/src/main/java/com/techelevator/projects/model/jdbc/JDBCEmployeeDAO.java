package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.EmployeeDAO;

public class JDBCEmployeeDAO implements EmployeeDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCEmployeeDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Employee> getAllEmployees() {
	String sqlQuery = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date FROM employee";
		
		ArrayList<Employee> employeeList = new ArrayList<>();
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		while (results.next()) {
			
			employeeList.add(mapRowToEmployee(results));
		}
		
		return employeeList;
	}

	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
		ArrayList<Employee> employeeNames = new ArrayList<>();
		
		String sqlQuery = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date FROM employee WHERE first_name ILIKE ? AND last_name ILIKE ?";
				
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, "%" + firstNameSearch + "%", "%" + lastNameSearch + "%");
		
		while (results.next()) {
			Employee theEmployee = mapRowToEmployee(results);
			
			employeeNames.add(theEmployee);
		}
		return employeeNames;
	}

	@Override
	public List<Employee> getEmployeesByDepartmentId(long id) {
		ArrayList<Employee> employeeDeptId = new ArrayList<>();
		String sqlQuery = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date FROM employee WHERE department_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, id);
		
		while (results.next()) {
			
			Employee theEmployee = mapRowToEmployee(results);
			
			employeeDeptId.add(theEmployee);
			
		}
		
		return employeeDeptId;
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		
		ArrayList<Employee> employeeWithoutProjects = new ArrayList<>();
		String sqlQuery = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date FROM employee WHERE department_id IS NULL";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		
		if (results.next()) {
			
			Employee theEmployee = mapRowToEmployee(results);
			
			employeeWithoutProjects.add(theEmployee);
			
		}
		
		return employeeWithoutProjects;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
		ArrayList<Employee> employeeByProjectId = new ArrayList<>();
		String sqlQuery = "SELECT employee.employee_id, employee.department_id, employee.first_name, employee.last_name, employee.birth_date, employee.gender, employee.hire_date "
				+ "FROM employee "
				+ "JOIN project_employee ON employee.employee_id = project_employee.employee_id "
				+ "WHERE project_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, projectId);
		while (results.next()) {
			Employee theEmployee = mapRowToEmployee(results);
			employeeByProjectId.add(theEmployee);
			
		}	
		return employeeByProjectId;
	}

	@Override
	public void changeEmployeeDepartment(Long employeeId, Long departmentId) {
		ArrayList<Employee> employeeChangeDept = new ArrayList<>();
		String sqlQuery = "UPDATE employee SET department_id = ? WHERE employee_id = ?";
		
		jdbcTemplate.update(sqlQuery, departmentId, employeeId);
		
	}
	
	private Employee mapRowToEmployee(SqlRowSet results) {
		Employee theEmployee = new Employee();
		theEmployee.setId(results.getLong("employee_id"));
		theEmployee.setDepartmentId(results.getLong("department_id"));
		theEmployee.setFirstName(results.getString("first_name"));
		theEmployee.setLastName(results.getString("last_name"));
		theEmployee.setBirthDay(results.getDate("birth_date").toLocalDate());
		theEmployee.setGender(results.getString("gender").charAt(0));
		theEmployee.setHireDate(results.getDate("hire_date").toLocalDate());

		return theEmployee;

}

}
