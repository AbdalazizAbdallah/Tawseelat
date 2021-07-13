package com.abdalazizabdallah.tawseelat.model;

public class CompanyInfo {
    private String companyName;
    private String companyLocation;
    private String companyType;

    public CompanyInfo(String companyName, String companyLocation, String companyType) {
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.companyType = companyType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }
}
