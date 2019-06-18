package com.techelevator.projects.view;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.jdbc.JDBCDepartmentDAO;

public class ProjectOrganizerDAOTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCDepartmentDAO departmentDAO;
	private static final String testDepartment = "Test Department";

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
		String sqlInsertDepartment = "INSERT INTO department (name) VALUES (?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertDepartment, testDepartment);
		departmentDAO = new JDBCDepartmentDAO(dataSource);
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
	public void retrieves_all_departments_should_return_5() {
		
		List<Department> results = departmentDAO.getAllDepartments();
		assertNotNull(results);
		assertEquals(5, results.size());
		
	}
	
	@Test
	public void search_by_name_test() {		
		
		List<Department> results = departmentDAO.searchDepartmentsByName(testDepartment);
		assertNotNull(results);
		assertEquals("[Test Department]", results.toString());
		
	}
	
	@Test
	public void create_department_shows_created_department() {
		
		long id = 5;

		Department testDepartment = createDepartmentForTest(id, "Pawnee");

		departmentDAO.createDepartment(testDepartment);
		
		List<Department> results = departmentDAO.searchDepartmentsByName("Pawnee");
		Department myDept = results.get(0);
				
		assertDepartmentsAreEqual(testDepartment, myDept);
	}
	
	
	private Department createDepartmentForTest(Long id, String name) {
		Department myDepartment = new Department();
		myDepartment.setId(id);
		myDepartment.setName(name);
		
		return myDepartment;
	}
	
	private void assertDepartmentsAreEqual(Department expected, Department actual) {
		//assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
	}
}
