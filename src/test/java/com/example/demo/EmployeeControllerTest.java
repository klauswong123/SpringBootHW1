package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	EmployeeRepository employeeRepository;

	@BeforeEach
	void cleanRepository(){
		employeeRepository.clearAll();
	}

	@Test
	void should_get_all_employees_when_perform_given_employees() throws Exception {
		//given
		Employee employee = new Employee("Klaus",1,20,99999999,"female");
		employeeRepository.create(employee);
		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(20))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Klaus"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("female"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(99999999));
		//then
	}

	@Test
	void should_return_employee_when_perform_post_given_employee() throws Exception {
		String employee="{\n" +
				"    \"name\": \"Klaus\",\n" +
				"    \"id\": 1,\n" +
				"    \"age\": 23,\n" +
				"    \"salary\": 123456,\n" +
				"    \"gender\": \"male\"\n" +
				"}";

		mockMvc.perform(post("/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(employee))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void should_get_employee_when_perform_get_given_id() throws Exception {
		//given
		Employee employee = new Employee("Klaus",1,20,99999999,"female");
		employeeRepository.create(employee);
		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/employees/1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Klaus"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(99999999));
		//then
	}

	@Test
	void should_get_employees_when_perform_get_given_page_and_pageSize() throws Exception {
		//given
		Employee employee1 = new Employee("Klaus",1,20,99999999,"female");
		employeeRepository.create(employee1);
		Employee employee2 = new Employee("Nick",2,50,1000,"male");
		employeeRepository.create(employee2);
		Employee employee3 = new Employee("Jack",3,60,1,"male");
		employeeRepository.create(employee3);
		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.get("/employees/?page=1&pageSize=2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(20))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Klaus"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("female"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(99999999))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].age").value(50))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Nick"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].gender").value("male"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].salary").value(1000));
	}

	@Test
	void should_get_employee_when_perform_get_given_gender() throws Exception {
		//given
		Employee employee = new Employee("Klaus",1,20,99999999,"female");
		employeeRepository.create(employee);
		Employee employee2 = new Employee("Nick",2,50,1000,"male");
		employeeRepository.create(employee2);
		Employee employee3 = new Employee("Jack",3,60,1,"male");
		employeeRepository.create(employee3);
		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/employees?gender=male"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(50))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Nick"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(1000))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].age").value(60))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jack"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].gender").value("male"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].salary").value(1));
		//then
	}

	@Test
	void should_return_employee_when_perform_put_given_updated_employee() throws Exception {
		//given
		Employee employee = new Employee("Klaus",1,20,99999999,"female");
		employeeRepository.create(employee);
		String updatedEmployee="{\n" +
				"    \"age\": 23,\n" +
				"    \"salary\": 123456\n" +
				"}";
		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.put("/employees/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatedEmployee))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$.age").value(23))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Klaus"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(123456));
	}

	@Test
	void should_delete_one_employee_when_perform_delete_given_id() throws Exception {
		//given
		Employee employee = new Employee("Klaus",1,20,99999999,"female");
		employeeRepository.create(employee);
		Employee employee2 = new Employee("Nick",2,50,1000,"male");
		employeeRepository.create(employee2);
		Employee employee3 = new Employee("Jack",3,60,1,"male");
		employeeRepository.create(employee3);
		//when
		mockMvc.perform(MockMvcRequestBuilders.delete("/employees/3"))
				.andExpect(MockMvcResultMatchers.status().isNoContent());

		//then
		assertEquals(2,employeeRepository.findAll().size());
	}

}
