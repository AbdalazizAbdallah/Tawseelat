package com.abdalazizabdallah.tawseelat.model;

import java.util.ArrayList;
import java.util.List;

public class CompanyTransportation {

    private String companyId;
    private CompanyInfo companyInfo;
    private List<Employee> employeeList;
    private String managerId;
    private int maxNoEmployee;

    public CompanyTransportation(String companyId, CompanyInfo companyInfo) {
        this.setCompanyId(companyId);
        this.setCompanyInfo(companyInfo);
        this.setEmployeeList(new ArrayList<>());
    }

    public CompanyTransportation(String companyId, CompanyInfo companyInfo, String managerId, int maxNoEmployee) {
        this(companyId, companyInfo);
        this.setManagerId(managerId);
        this.setMaxNoEmployee(maxNoEmployee);
    }

    public int getMaxNoEmployee() {
        return maxNoEmployee;
    }

    public void setMaxNoEmployee(int maxNoEmployee) {
        this.maxNoEmployee = maxNoEmployee;
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
