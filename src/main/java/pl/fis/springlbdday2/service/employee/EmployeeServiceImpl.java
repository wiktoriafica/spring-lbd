package pl.fis.springlbdday2.service.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fis.springlbdday2.entity.employee.Employee;
import pl.fis.springlbdday2.repository.employee.EmployeeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException("Employee with id " + id + " does not exists"));
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}
