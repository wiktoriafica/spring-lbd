package pl.fis.springlbd.service.employee;

import pl.fis.springlbd.entity.employee.Employee;

import java.util.List;

public interface EmployeeService {

    List findAll();

    String getEmployeeNickname(String firstName, String lastName);

    Employee findByName(String name);

    Employee save(Employee employee);
}
