package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.DepartmentDAO;

public class JDBCDepartmentDAO implements DepartmentDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCDepartmentDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Department> getAllDepartments() {
		String sqlQuery = "SELECT department_id, name FROM department";
		
		ArrayList<Department> departmentList = new ArrayList<>();
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		while (results.next()) {
			
			departmentList.add(mapRowToDepartment(results));
		}
		
		return departmentList;
	}

	@Override
	public List<Department> searchDepartmentsByName(String nameSearch) {
		ArrayList <Department> departments = new ArrayList<>();

		String sqlQuery = "SELECT department_id, name FROM department WHERE name ILIKE ?";
				
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, "%" + nameSearch + "%");
		
		while (results.next()) {
			Department theDepartment = mapRowToDepartment(results);
			
			departments.add(theDepartment);
		}
		
		return departments;
	}

	@Override
	public void saveDepartment(Department updatedDepartment) {
		
		String sqlQuery = "UPDATE department SET name = ? WHERE department_id = ?";
		
		jdbcTemplate.update(sqlQuery, updatedDepartment.getName(), updatedDepartment.getId());
		
	}

	@Override
	public Department createDepartment(Department newDepartment) {
		
		String sqlQuery = "INSERT INTO department (name) VALUES (?)";
		
		jdbcTemplate.update(sqlQuery, newDepartment.getName());
	
		return newDepartment;
	}

	@Override
	public Department getDepartmentById(Long id) {
		Department theDepartment = null;
		String sqlFindDepartmentById = "SELECT department_id, name FROM department WHERE department_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindDepartmentById, id);
		if (results.next()) {
			
			theDepartment = mapRowToDepartment(results);
		}
		
		return theDepartment;
	}
	
	private Department mapRowToDepartment(SqlRowSet results) {
		Department theDepartment = new Department();
		theDepartment.setId(results.getLong("department_id"));
		theDepartment.setName(results.getString("name"));
		return theDepartment;

}
	
}
