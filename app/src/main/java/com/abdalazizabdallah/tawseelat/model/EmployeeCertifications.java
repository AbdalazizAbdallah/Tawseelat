package com.abdalazizabdallah.tawseelat.model;

public class EmployeeCertifications {

    private String drivingLicensePicturePath;
    private String vehicleLicensePicturePath;
    private String idPicturePath;


    public EmployeeCertifications(String drivingLicensePicturePath, String vehicleLicensePicturePath, String idPicturePath) {
        this.drivingLicensePicturePath = drivingLicensePicturePath;
        this.vehicleLicensePicturePath = vehicleLicensePicturePath;
        this.idPicturePath = idPicturePath;
    }

    public String getDrivingLicensePicturePath() {
        return drivingLicensePicturePath;
    }

    public void setDrivingLicensePicturePath(String drivingLicensePicturePath) {
        this.drivingLicensePicturePath = drivingLicensePicturePath;
    }

    public String getVehicleLicensePicturePath() {
        return vehicleLicensePicturePath;
    }

    public void setVehicleLicensePicturePath(String vehicleLicensePicturePath) {
        this.vehicleLicensePicturePath = vehicleLicensePicturePath;
    }

    public String getIdPicturePath() {
        return idPicturePath;
    }

    public void setIdPicturePath(String idPicturePath) {
        this.idPicturePath = idPicturePath;
    }
}
