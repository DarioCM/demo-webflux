package dev.dario.webflux.employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

  Mono<EmployeeDto> saveEmployee(EmployeeDto dto);

  Mono<EmployeeDto> getEmployee(String employeeId);

  Flux<EmployeeDto> getAllEmployees();

  Mono<EmployeeDto> updateEmployee(EmployeeDto dto, String employeeId);

}
