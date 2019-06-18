package com.techelevator.projects.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.jdbc.JDBCEmployeeDAO;
import com.techelevator.projects.model.jdbc.JDBCProjectDAO;

public class JDBCProjectDAOTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCProjectDAO projectDAO;
	private JDBCEmployeeDAO employeeDAO;
	private static final String testProject = "Test Project";
	private static final Long testId = (long) 7;


	@BeforeClass

	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/projects");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/*
		 * The following line disables autocommit for connections returned by this
		 * DataSource. This allows us to rollback any changes after each test
		 */
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setUp() throws Exception {
		String sqlInsertProject = "INSERT INTO project (project_id, name) VALUES (?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertProject, testId, testProject);
		projectDAO = new JDBCProjectDAO(dataSource);
	}

	/*
	 * After each test, we rollback any changes that were made to the database so
	 * that everything is clean for the next test
	 */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void test_getAllActiveProjects() {
		List<Project> allProjects = projectDAO.getAllActiveProjects();
		Assert.assertEquals(allProjects.get(0).toString(), projectDAO.getAllActiveProjects().get(0).toString());
		
	}
	
	@Test
	public void test_removeEmployeeFromProject()  {
		employeeDAO = new JDBCEmployeeDAO(dataSource);
		projectDAO.removeEmployeeFromProject((long)1, (long)5);
		projectDAO.removeEmployeeFromProject((long)1, (long)3);
		List<Employee> employeeList = employeeDAO.getEmployeesByProjectId((long)1);
		assertEquals(0, employeeList.size()); // should be zero employees left on projectID "1"
	}
	
	
	@Test
	public void test_addEmployeeFromProject()  {
		employeeDAO = new JDBCEmployeeDAO(dataSource);
		projectDAO.addEmployeeToProject((long)1, (long)10);
		projectDAO.addEmployeeToProject((long)1, (long)11);
		List<Employee> employeeList = employeeDAO.getEmployeesByProjectId((long)1);
		assertEquals(4, employeeList.size()); // should be zero employees left on projectID "1"
	}


}
