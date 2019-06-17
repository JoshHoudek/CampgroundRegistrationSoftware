package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.ProjectDAO;

public class JDBCProjectDAO implements ProjectDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCProjectDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Project> getAllActiveProjects() {
		
		String sqlQuery = "SELECT project_id, name, from_date, to_date FROM project WHERE from_date IS NOT NULL AND from_date < now() AND (to_date >= now() OR to_date IS NULL)";
		
		ArrayList<Project> projectList = new ArrayList<>();
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		while (results.next()) {
			
			projectList.add(mapRowToActiveProject(results));
		}
		
		return projectList;
	}

	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {
		
		String sqlQuery = "DELETE FROM project_employee WHERE project_id = ? AND employee_id = ?";
		
		jdbcTemplate.update(sqlQuery, projectId, employeeId);

		
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) {
		
		String sqlQuery = "INSERT INTO project_employee (project_id, employee_id)"
				+ "VALUES (?,?)";
		
		jdbcTemplate.update(sqlQuery, projectId, employeeId);
		
		
	}
	
	private Project mapRowToActiveProject(SqlRowSet results) {
		Project theProject = new Project();
		try {
		theProject.setId(results.getLong("project_id"));
		theProject.setName(results.getString("name"));
		theProject.setStartDate(results.getDate("from_date").toLocalDate());
		theProject.setEndDate(results.getDate("to_date").toLocalDate());
		
		}
		catch (Exception e) {
			// for some of the methods, they do have null values which are acceptable  
		}
		
		return theProject;

}
	
}