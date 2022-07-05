package pl.fis.springlbd.entity.employee;

public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String pesel;
    private String IDNumber;

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, String phoneNumber, String pesel, String IDNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
        this.IDNumber = IDNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }
}
