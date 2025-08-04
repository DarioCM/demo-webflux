package dev.dario.webflux.employee;

import org.springframework.stereotype.Service;
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
}
