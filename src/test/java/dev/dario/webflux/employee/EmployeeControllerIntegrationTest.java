package dev.dario.webflux.employee;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class EmployeeControllerIntegrationTest {

  @Autowired
  private EmployeeService service;

  @Autowired
  private EmployeeRepository repository;

  @Autowired
  private WebTestClient webTestClient;

  @BeforeEach
  void before(){
    repository.deleteAll().subscribe();
  }

  @Test
  void testSaveEmployee() {

    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("casta");
    employeeDto.setLastName("carlos");
    employeeDto.setEmail("dario20049@gmail.com");

    webTestClient.post().uri("/api/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(employeeDto), EmployeeDto.class)
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
        .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
        .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
  }

  @Test
  void testGetSingleEmployee() {

    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("casta");
    employeeDto.setLastName("carlos");
    employeeDto.setEmail("dario20049@gmail.com");

    EmployeeDto savedEmployee = service.saveEmployee(employeeDto).block();

    webTestClient.get()
        .uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
        .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
        .jsonPath("$.email").isEqualTo(employeeDto.getEmail())
        .jsonPath("$.id").isEqualTo(savedEmployee.getId());

  }

  @Test
  void testGetAllEmployees() {

    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("casta");
    employeeDto.setLastName("carlos");
    employeeDto.setEmail("dario20049@gmail.com");
    service.saveEmployee(employeeDto).block();

    EmployeeDto employeeDto2 = new EmployeeDto();
    employeeDto2.setFirstName("casta2");
    employeeDto2.setLastName("carlos2");
    employeeDto2.setEmail("dario20049@gmail.com");
    service.saveEmployee(employeeDto2).block();


    webTestClient.get().uri("/api/employees")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(EmployeeDto.class)
        .consumeWith(System.out::println);
  }

  @Test
  void testUpdateEmployee(){

    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("casta");
    employeeDto.setLastName("carlos");
    employeeDto.setEmail("dario20049@gmail.com");
    EmployeeDto savedEmployee = service.saveEmployee(employeeDto).block();

    EmployeeDto updateEmployee = new EmployeeDto();
    updateEmployee.setFirstName("mendoza");
    updateEmployee.setLastName("dario");
    updateEmployee.setEmail("change9@gmail.com");

    webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(updateEmployee), EmployeeDto.class)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.firstName").isEqualTo(updateEmployee.getFirstName())
        .jsonPath("$.lastName").isEqualTo(updateEmployee.getLastName())
        .jsonPath("$.email").isEqualTo(updateEmployee.getEmail());

  }

  @Test
  void testDeleteEmployee(){

    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("casta");
    employeeDto.setLastName("carlos");
    employeeDto.setEmail("dario20049@gmail.com");
    EmployeeDto savedEmployee = service.saveEmployee(employeeDto).block();

    webTestClient.delete().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
        .exchange()
        .expectStatus().isNoContent()
        .expectBody()
        .consumeWith(System.out::println);



  }

}
