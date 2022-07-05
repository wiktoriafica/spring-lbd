package pl.fis.springlbd.service.employee;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        String nickname = employeeService.getEmployeeNickname(firstName, lastName);
        assertEquals(expectedNickname, nickname);
    }
}
