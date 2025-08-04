package dev.dario.webflux.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  private EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  //Build reactive save  employee rest api
  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto dto){
    return employeeService.saveEmployee(dto);
  }

  // build reactive get single employee
  @GetMapping("{id}")
  public Mono<EmployeeDto> getEmployeeById(@PathVariable("id") String employeeId){
    return employeeService.getEmployee(employeeId);
  }






}
