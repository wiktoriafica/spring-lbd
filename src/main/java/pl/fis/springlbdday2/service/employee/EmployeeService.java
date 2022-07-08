package pl.fis.springlbdday2.service.employee;

import pl.fis.springlbdday2.entity.employee.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    void addEmployee(Employee employee);
}
