package dev.dario.webflux.employee;

import reactor.core.publisher.Mono;

public interface EmployeeService {

  Mono<EmployeeDto> saveEmployee(EmployeeDto dto);

}
