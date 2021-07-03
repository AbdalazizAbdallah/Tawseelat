package com.abdalazizabdallah.tawseelat.model;

public class TripsRequestsHistory {

    private String source;
    private String destination;
    private String employeeName;
    private String clientName;
    private String companyName;
    private String acceptedDate;
    private String finishDate;
    private String nearestPlace;


    public TripsRequestsHistory(String source, String destination, String employeeName, String clientName, String companyName, String acceptedDate, String finishDate, String nearestPlace) {
        this.source = source;
        this.destination = destination;
        this.employeeName = employeeName;
        this.clientName = clientName;
        this.companyName = companyName;
        this.acceptedDate = acceptedDate;
        this.finishDate = finishDate;
        this.nearestPlace = nearestPlace;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(String acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getNearestPlace() {
        return nearestPlace;
    }

    public void setNearestPlace(String nearestPlace) {
        this.nearestPlace = nearestPlace;
    }


    @Override
    public String toString() {
        return "TripsRequestsHistory{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", acceptedDate='" + acceptedDate + '\'' +
                ", finishDate='" + finishDate + '\'' +
                ", nearestPlace='" + nearestPlace + '\'' +
                '}';
    }
}
