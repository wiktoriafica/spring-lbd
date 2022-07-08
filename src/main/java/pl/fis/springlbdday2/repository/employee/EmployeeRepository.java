package pl.fis.springlbdday2.repository.employee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.fis.springlbdday2.entity.employee.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}