package com.abdalazizabdallah.tawseelat.model;

public class ClientInformation {

    private String clientName;
    private String clientGender;
    private String clientId;
    private String clientDOB;

    public ClientInformation(String clientName) {
        this.clientName = clientName;
    }

    public ClientInformation(String clientName, String clientGender, String clientId, String clientDOB) {
        this.clientName = clientName;
        this.clientGender = clientGender;
        this.clientId = clientId;
        this.clientDOB = clientDOB;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientGender() {
        return clientGender;
    }

    public void setClientGender(String clientGender) {
        this.clientGender = clientGender;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientDOB() {
        return clientDOB;
    }

    public void setClientDOB(String clientDOB) {
        this.clientDOB = clientDOB;
    }
}
