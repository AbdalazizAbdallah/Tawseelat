package com.abdalazizabdallah.tawseelat.model;

public class Client {

    private String idClientAccount;
    private ClientInformation clientInformation;
    private AccountPassword accountPassword;
    private String emailClient;
    private String pathPicture;
    private String phoneNumber;

    public Client() {

    }

    public Client(String idClientAccount, ClientInformation clientInformation, AccountPassword accountPassword, String emailClient) {
        this.idClientAccount = idClientAccount;
        this.clientInformation = clientInformation;
        this.accountPassword = accountPassword;
        this.emailClient = emailClient;
    }

    public String getIdClientAccount() {
        return idClientAccount;
    }

    public void setIdClientAccount(String idClientAccount) {
        this.idClientAccount = idClientAccount;
    }

    public ClientInformation getClientInformation() {
        return clientInformation;
    }

    public void setClientInformation(ClientInformation clientInformation) {
        this.clientInformation = clientInformation;
    }

    public AccountPassword getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(AccountPassword accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getPathPicture() {
        return pathPicture;
    }

    public void setPathPicture(String pathPicture) {
        this.pathPicture = pathPicture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
