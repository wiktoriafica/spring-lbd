package pl.fis.springlbd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import pl.fis.springlbd.entity.employee.Employee;
import pl.fis.springlbd.service.employee.EmployeeService;
import pl.fis.springlbd.service.employee.EmployeeServiceImpl2;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class SpringLbdApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLbdApplication.class);
    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringLbdApplication.class, args);
    }

    @PostConstruct
    public void onConstructedEmployeeService() {
        employeeService.save(new Employee(1L, "Wiktoria", "Fica",
                "123456789","1111111111", "ABC123456"));
        employeeService.save(new Employee(2L, "Jan", "Kowalski",
                "123456789","1111111111", "ABC123456"));
        List<Employee> employeesList = employeeService.findAll();
        LOGGER.info("EmployeeService bean was constructed, its type: {}", employeeService.getClass());
    }
}
