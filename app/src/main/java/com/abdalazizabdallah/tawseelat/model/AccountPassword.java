package com.abdalazizabdallah.tawseelat.model;

import java.util.ArrayList;
import java.util.List;

public class AccountPassword {

    private List<String> previousPassword;
    private String currentPassword;

    public AccountPassword(String currentPassword) {
        this.currentPassword = currentPassword;
        previousPassword = new ArrayList<>();
        previousPassword.add(currentPassword);
    }

    public boolean isPasswordToken(String password) {
        for (String pass : previousPassword) {
            if (pass.equals(password)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getPreviousPassword() {
        return previousPassword;
    }

    private void setPreviousPassword(List<String> previousPassword) {
        this.previousPassword = previousPassword;
    }


    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
