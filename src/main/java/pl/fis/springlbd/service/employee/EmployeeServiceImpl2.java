package pl.fis.springlbd.service.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.fis.springlbd.entity.employee.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("prod")
@Service
public class EmployeeServiceImpl2 implements EmployeeService{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl2.class);
    private final Map<Long, Employee> employees = new HashMap<>();
    @Value("${project.prefix}")
    private String prefix;

    @Value("${project.suffix}")
    private String suffix;
    @Override
    public List findAll() {
        return null;
    }

    @Override
    public String getEmployeeNickname(String firstName, String lastName) {
        LOGGER.info("Passed first name: {}", firstName);
        LOGGER.info("Passed last name: {}", lastName);
        String nickname = prefix + firstName.substring(0, Math.min(3, firstName.length()))
                + lastName.substring(0, Math.min(3, lastName.length())) + suffix;
        LOGGER.info("Created nickname: {}", nickname);
        return nickname;
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
