package com.abdalazizabdallah.tawseelat.model;

import java.util.List;

public class CompanyTransportation {

    private String companyId;
    private CompanyInfo companyInfo;
    private List<Employee> employeeList;
    private String managerId;

    public CompanyTransportation(String companyId, CompanyInfo companyInfo) {
        this.companyId = companyId;
        this.companyInfo = companyInfo;
    }

    public CompanyTransportation(String companyId) {
        this.companyId = companyId;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public CompanyTransportation(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
}
