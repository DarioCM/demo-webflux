package dev.dario.webflux.employee;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockitoBean
  private EmployeeService employeeService;

  @Test
  void givenEmployeeObject_whenSaveEmployeee_thenReturnSavedEmployee() {

    //given - pre conditions or set up
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setEmail("dario@gmail.com");
    employeeDto.setFirstName("dario");
    employeeDto.setLastName("castaaa");

    BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(EmployeeDto.class)))
        .willReturn(Mono.just(employeeDto));

    // when - action or behaviour
    WebTestClient.ResponseSpec response =
        webTestClient.post().uri("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(employeeDto), EmployeeDto.class)
            .exchange();

    // then - verifyy the result
    response
        .expectStatus()
        .isCreated()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
        .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName());

  }

  @Test
  void givenEmployeeId_whenGetEmployee_thenReturnEmloyeeObject() {
    // given - pre-conditions
    String employeeId = "123";

    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setEmail("dario@gmail.com");
    employeeDto.setFirstName("dario");
    employeeDto.setLastName("casta");

    BDDMockito.given(employeeService.getEmployee(employeeId)).willReturn(Mono.just(employeeDto));

    // when - action
    WebTestClient.ResponseSpec responseSpec =
        webTestClient.get()
            .uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
            .exchange();

    // then - verify - output
    responseSpec.expectStatus().isOk()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
        .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
        .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
  }

  //get all employees
  @Test
  void givenListOfEmployees_whenGetAllEmployees_returnListOfEmployees() {
    //given - pre conditions
    List<EmployeeDto> list = new ArrayList<>();
    EmployeeDto employeeDto1 = new EmployeeDto();
    employeeDto1.setEmail("dario@gmail.com");
    employeeDto1.setFirstName("dario");
    employeeDto1.setLastName("casta");
    list.add(employeeDto1);
    EmployeeDto employeeDto2 = new EmployeeDto();
    employeeDto2.setEmail("carlos@gmail.com");
    employeeDto2.setFirstName("carlos");
    employeeDto2.setLastName("casta");
    list.add(employeeDto2);

    Flux<EmployeeDto> employeeDtoFlux = Flux.fromIterable(list);

    BDDMockito.given(employeeService.getAllEmployees()).willReturn(employeeDtoFlux);

    //when - action or behaviour
    WebTestClient.ResponseSpec responseSpec =
        webTestClient.get().uri("/api/employees")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

    //then - verify
    responseSpec.expectStatus().isOk()
        .expectBodyList(EmployeeDto.class)
        .consumeWith(System.out::println);

  }


  // test updating
  @Test
  void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {
    //given - pre conditions
    String employeeId = "123";

    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("carlos");
    employeeDto.setLastName("casta");
    employeeDto.setEmail("dario@gmail.com");

    BDDMockito.given(
            employeeService.updateEmployee(ArgumentMatchers.any(EmployeeDto.class),
                ArgumentMatchers.anyString()))
        .willReturn(Mono.just(employeeDto));

    //when - action or behaviour UPDATE => PUT
    WebTestClient.ResponseSpec response =
        webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(employeeDto), EmployeeDto.class)
            .exchange();

    // then - verify
    response.expectStatus().isOk()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
        .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
        .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

  }

  @Test
  void givenEmployeeId_whenDeleteEmployee_thenReturnNothing(){
    // given - pre conditions
    String employeeId = "123";
    Mono<Void> voidMono = Mono.empty();
    BDDMockito.given(employeeService.deleteEmployee(employeeId))
        .willReturn(voidMono);
    // when - action or behaviour
    WebTestClient.ResponseSpec response =
        webTestClient.delete()
            .uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
            .exchange();
    // then - verify the output
    response.expectStatus().isNoContent()
        .expectBody()
        .consumeWith(System.out::println);


  }


}