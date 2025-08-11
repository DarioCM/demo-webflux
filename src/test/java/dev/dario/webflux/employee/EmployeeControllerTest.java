package dev.dario.webflux.employee;


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


}