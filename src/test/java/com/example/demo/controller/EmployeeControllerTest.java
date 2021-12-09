package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	EmployeeRepository employeeRepository;

	@BeforeEach
	@AfterEach
	void cleanRepo(){
		employeeRepository.deleteAll();
	}

	private Employee getSingleEmployee(){
		return new Employee("Klaus",20,99999999,"female");
	}

	private List<Employee> getEmployees(){
		return List.of(
				new Employee("Klaus",20,99999999,"female"),
				new Employee("Jason",24,123,"male"),
				new Employee("Kam",21,112312323,"male"),
				new Employee("jenn",24,123,"female")
		);
	}

	@Test
	void should_get_all_employees_when_perform_given_employees() throws Exception {
		//given
		Employee employee = getSingleEmployee();
		employeeRepository.insert(employee);
		//when
		mockMvc.perform(get("/employees"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isString())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employee.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(employee.getAge()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value(employee.getGender()));
		//then
	}

	@Test
	void should_return_employee_when_perform_get_given_employee_id() throws Exception  {
		//given
		Employee employee = getSingleEmployee();
		Employee saveEmployee = employeeRepository.insert(employee);
		//when
		//then
		mockMvc.perform(get("/employees/" + saveEmployee.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(saveEmployee.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.age").value(employee.getAge()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(employee.getGender()));
	}

	@Test
	void should_return_employee_when_perform_post_given_employee() throws Exception {
		employeeRepository.insert(getSingleEmployee());
		String employee="{\n" +
				"    \"name\": \"Klaus\",\n" +
				"    \"age\": 23,\n" +
				"    \"salary\": 123456,\n" +
				"    \"gender\": \"male\"\n" +
				"}";

		mockMvc.perform(post("/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(employee))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.age").value(23))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Klaus"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(123456));
	}


	@Test
	void should_get_employees_when_perform_get_given_page_and_pageSize() throws Exception {
		//given
		Employee employee1 = new Employee("Klaus","1",20,99999999,"female","1");
		employeeRepository.insert(employee1);
		Employee employee2 = new Employee("Nick","2",50,1000,"male","1");
		employeeRepository.insert(employee2);
		Employee employee3 = new Employee("Jack","3",60,1,"male","1");
		employeeRepository.insert(employee3);
		//when
		//then
		mockMvc.perform(get("/employees/?page=1&pageSize=2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isString())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(20))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Klaus"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("female"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").isString())
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].age").value(50))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Nick"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].gender").value("male"));
	}

	@Test
	void should_get_employee_when_perform_get_given_gender() throws Exception {
		//given
		Employee employee = new Employee("Klaus","1",20,99999999,"female","1");
		employeeRepository.insert(employee);
		Employee employee2 = new Employee("Nick","2",50,1000,"male","1");
		employeeRepository.insert(employee2);
		Employee employee3 = new Employee("Jack","3",60,1,"male","1");
		employeeRepository.insert(employee3);
		//when
		mockMvc.perform(get("/employees?gender=male"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Nick", "Jack")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].age", containsInAnyOrder(50, 60)));
		//then
	}

	@Test
	void should_return_employee_when_perform_put_given_updated_employee() throws Exception {
		//given
		Employee employee = getSingleEmployee();
		Employee employee1 = employeeRepository.save(employee);
		String updatedEmployee="{\n" +
				"    \"age\": 23,\n" +
				"    \"salary\": 123456\n" +
				"}";
		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}",employee1.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatedEmployee))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
				.andExpect(MockMvcResultMatchers.jsonPath("$.age").value(23))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Klaus"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(123456));
	}

	@Test
	void should_delete_one_employee_when_perform_delete_given_id() throws Exception {
		//given
		Employee employee = getSingleEmployee();
		Employee employee1 = employeeRepository.insert(employee);
		//when
		mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}",employee1.getId()))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		//then
		assertEquals(0, employeeRepository.findAll().size());
	}

	@Test
	void should_return_notFound_employee_when_perform_get_given_employee_and_falseId() throws Exception {
		//given
		Employee employee1 = getSingleEmployee();
		Employee employee3 = employeeRepository.insert(employee1);
		employeeRepository.delete(employee3);
		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}",employee3.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
