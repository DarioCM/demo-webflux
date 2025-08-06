package dev.dario.webflux.employee;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class EmployeeServiceImpl implements EmployeeService {

  private EmployeeRepository repository;

  public EmployeeServiceImpl(EmployeeRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<EmployeeDto> saveEmployee(EmployeeDto dto) {

    Employee employeeDoc = EmployeeMapper.mapToDoc(dto);
    Mono<Employee> savedEmployee = repository.save(employeeDoc);
    //mono has a map, remember is functional programing, has core interfaces
    return savedEmployee.map(EmployeeMapper::mapToDto);

  }

  @Override
  public Mono<EmployeeDto> getEmployee(String employeeId) {

    Mono<Employee> employee = repository.findById(employeeId);
    return employee.map(EmployeeMapper::mapToDto);

  }

  @Override
  public Flux<EmployeeDto> getAllEmployees() {

    Flux<Employee> listFlux = repository.findAll();
    return listFlux
        .map(EmployeeMapper::mapToDto)
        .switchIfEmpty(Flux.empty());

  }

  @Override
  public Mono<EmployeeDto> updateEmployee(EmployeeDto dto, String employeeId) {

    Mono<Employee> employeeMono = repository.findById(employeeId);

    Mono<Employee> updateEmployee =
        employeeMono.flatMap((existingEmployee) -> {
          existingEmployee.setFirstName(dto.getFirstName());
          existingEmployee.setLastName(dto.getLastName());
          existingEmployee.setEmail(dto.getEmail());
          return repository.save(existingEmployee);
        });

    return updateEmployee.map(EmployeeMapper::mapToDto);

  }

  @Override
  public Mono<Void> deleteEmployee(String employeeId) {
    return repository.deleteById(employeeId);
  }
}
