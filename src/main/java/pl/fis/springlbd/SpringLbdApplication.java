package pl.fis.springlbd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.fis.springlbd.service.employee.EmployeeService;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class SpringLbdApplication {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringLbdApplication.class, args);
    }

    @PostConstruct
    public void findAll() {
        List employeesList = employeeService.findAll();
    }

}
