package com.abdalazizabdallah.tawseelat.model;

public class Employee extends Client {

    private String idEmployee;
    private EmployeeCertifications employeeCertifications;

    public Employee(String idClientAccount, ClientInformation clientInformation,
                    AccountPassword accountPassword, String emailClient, EmployeeCertifications employeeCertifications) {
        super(idClientAccount, clientInformation, accountPassword, emailClient);

        this.employeeCertifications = employeeCertifications;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public EmployeeCertifications getEmployeeCertifications() {
        return employeeCertifications;
    }

    public void setEmployeeCertifications(EmployeeCertifications employeeCertifications) {
        this.employeeCertifications = employeeCertifications;
    }
}
