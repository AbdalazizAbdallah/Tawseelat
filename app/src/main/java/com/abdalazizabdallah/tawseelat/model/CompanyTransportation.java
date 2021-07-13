package com.abdalazizabdallah.tawseelat.model;

public class CompanyTransportation {

    private String companyId;
    private CompanyInfo companyInfo;
    //private List<Employee> employeeList;
    private String managerId;

    public CompanyTransportation(String companyId, CompanyInfo companyInfo, String managerId) {
        this.companyId = companyId;
        this.companyInfo = companyInfo;
        this.managerId = managerId;
    }

    public CompanyTransportation(String companyId, String managerId) {
        this.companyId = companyId;
        this.managerId = managerId;
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
