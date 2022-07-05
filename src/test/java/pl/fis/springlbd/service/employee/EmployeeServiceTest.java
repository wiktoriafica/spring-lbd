package pl.fis.springlbd.service.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.fis.springlbd.entity.employee.Employee;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeServiceTest {
    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ParameterizedTest
    @CsvSource({"Wiktoria, Fica, EMPWikFic647", "Adam, Kowalski, EMPAdaKow647", "Aa, Bb, EMPAaBb647"})
    public void getEmployeeNicknameTest(String firstName, String lastName, String expectedNickname) {
        //WHEN
        String nickname = employeeService.getEmployeeNickname(firstName, lastName);
        //THEN
        assertEquals(expectedNickname, nickname);
    }

    @Test
    public void saveEmployeeTest() {
        Employee savedEmployee = employeeService.save(new Employee(1L, "Wiktoria", "Fica",
                "123456789","1111111111", "ABC123456"));
        assertNotNull(savedEmployee);
    }

    @Test
    public void getEmployeeByNameTest() {
        //GIVEN
        Employee expectedEmployee1 = new Employee(2L, "Adam", "Kowalski",
                "123456789", "1111111111", "ABC123456");
        Employee expectedEmployee2 = new Employee(3L, "Jan", "Nowak",
                "123456789", "1111111111", "ABC123456");
        employeeService.save(expectedEmployee1);
        employeeService.save(expectedEmployee2);
        //WHEN
        Employee employee1 = employeeService.findByName("Adam");
        Employee employee2 = employeeService.findByName("Nowak");
        //THEN
        assertEquals(expectedEmployee1, employee1);
        assertEquals(expectedEmployee2, employee2);
    }
}
