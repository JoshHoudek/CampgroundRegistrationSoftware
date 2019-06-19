package com.techelevator.projects.view;

import java.sql.SQLException;
import java.util.Date;
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
import com.techelevator.projects.model.jdbc.JDBCEmployeeDAO;

public class JDBCEmployeeDAOTest {
	private static SingleConnectionDataSource dataSource;
	private JDBCEmployeeDAO employeeDAO;

	private static final Long testEmployeeId = (long) 13;
	private static final Long testDepartmentId = (long) 4;
	private static final String testFirstName = "John";
	private static final String testLastName = "Doe";
	private static final Date testBirthDate = new Date();
	private static final char testGender = 'M';
	private static final Date testHireDate = new Date();

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
		String sqlInsertProject = "INSERT INTO employee (employee_id, department_id, first_name, last_name, birth_date, gender, hire_date) VALUES (?,?, ?, ?, ?, ?, ?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertProject, testEmployeeId, testDepartmentId, testFirstName, testLastName,
				testBirthDate, testGender, testHireDate);
		employeeDAO = new JDBCEmployeeDAO(dataSource);
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
	public void testGetAllEmployees() {
		List<Employee> allEmployees = employeeDAO.getAllEmployees();
		Assert.assertEquals("Doe, John", employeeDAO.getAllEmployees().get(12).toString());
		Assert.assertEquals(13, employeeDAO.getAllEmployees().size());

	}

	@Test
	public void testSearchEmployeesByName() {
		List<Employee> allEmployees = employeeDAO.searchEmployeesByName("John", "Doe");
		Assert.assertEquals("Doe, John", allEmployees.get(0).toString());
		Assert.assertEquals(1, allEmployees.size());

	}

	@Test
	public void testGetEmployeesByDepartmentId() {
		Long testDepartmentId2 = (long) 1;

		List<Employee> employeeDepartmentIdSearch = employeeDAO.getEmployeesByDepartmentId(testDepartmentId);
		Assert.assertEquals(3, employeeDepartmentIdSearch.size());
		List<Employee> employeeDepartmentIdSearch2 = employeeDAO.getEmployeesByDepartmentId(testDepartmentId2);
		Assert.assertEquals(1, employeeDepartmentIdSearch2.size());
	}

	@Test
	public void testGetEmployeesWithoutProjects() {
		List<Employee> employeesWithoutProjects = employeeDAO.getEmployeesWithoutProjects();
		Assert.assertEquals(1, employeesWithoutProjects.size());

	}

	@Test
	public void testGetEmployeesByProjectId() {
		Long testProjectId = (long) 5;
		Long testProjectId2 = (long) 2;

		List<Employee> testProjectIdSearch = employeeDAO.getEmployeesByProjectId(testProjectId);
		Assert.assertEquals(2, testProjectIdSearch.size());

		List<Employee> testProjectIdSearch2 = employeeDAO.getEmployeesByProjectId(testProjectId2);
		Assert.assertEquals(0, testProjectIdSearch2.size());
	}

	@Test
	public void testChangeEmployeeDepartment() {
		Long testEmployeeId = (long) 1;
		Long testDepartmentId = (long) 1;

		employeeDAO.changeEmployeeDepartment(testEmployeeId, testDepartmentId);
		Assert.assertEquals(2, employeeDAO.getEmployeesByDepartmentId(testDepartmentId).size());
		Assert.assertEquals("[Henderson, Flo, Keppard, Fredrick]",
				employeeDAO.getEmployeesByDepartmentId(testDepartmentId).toString());

	}

}