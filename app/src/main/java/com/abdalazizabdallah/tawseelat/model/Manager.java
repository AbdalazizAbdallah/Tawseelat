package com.abdalazizabdallah.tawseelat.model;

public class Manager extends Employee {

    private final String idManger;
    private final CompanyTransportation companyTransportation;

    public Manager(String idClientAccount, ClientInformation clientInformation, AccountPassword accountPassword,
                   String emailClient, EmployeeCertifications employeeCertifications, String idManger
            , CompanyTransportation companyTransportation
    ) {
        super(idClientAccount, clientInformation, accountPassword, emailClient, employeeCertifications);
        this.idManger = idManger;
        this.companyTransportation = companyTransportation;
        companyTransportation.setManagerId(idManger);
    }
}
