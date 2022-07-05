package pl.fis.springlbd.service.employee;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.fis.springlbd.entity.employee.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Profile("dev")
@Service
public class EmployeeServiceImpl1 implements EmployeeService {

    @Value("${project.prefix}")
    private String prefix;

    @Value("${project.suffix}")
    private String suffix;

    private Map<Long, Employee> employees = new HashMap<>();

    @Override
    public List findAll() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public String getEmployeeNickname(String firstName, String lastName) {
        return prefix + firstName.substring(0, Math.min(3, firstName.length()))
                + lastName.substring(0, Math.min(3, lastName.length())) + suffix;
    }

    @Override
    public Employee findByName(String name) {
        if(name == null)
            return null;
        for(Map.Entry<Long, Employee> employee : employees.entrySet()) {
            if(employee.getValue().getFirstName().equals(name) || employee.getValue().getLastName().equals(name))
                return employee.getValue();
        }
        return null;
    }

    @Override
    public Employee save(Employee employee) {
        if(employee != null) {
            employees.put(employee.getId(), employee);
            return employees.get(employee.getId());
        }
        return null;
    }
}
