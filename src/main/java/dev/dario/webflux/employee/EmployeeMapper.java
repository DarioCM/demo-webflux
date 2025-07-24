package dev.dario.webflux.employee;

public class EmployeeMapper {

  public static EmployeeDto mapToDto(Employee employee){
    return new EmployeeDto(
        employee.getId(),
        employee.getFirstName(),
        employee.getLastName(),
        employee.getEmail()
    );
  }

  public static Employee mapToDoc(EmployeeDto dto) {
    return new Employee(
        dto.getId(),
        dto.getFirstName(),
        dto.getLastName(),
        dto.getEmail()
    );
  }

}
