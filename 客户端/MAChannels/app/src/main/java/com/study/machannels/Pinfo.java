package com.study.machannels;

public class Pinfo {
    public String location;
    public String treatment;
    public String operation;
    public String anatomy;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAnatomy() {
        return anatomy;
    }

    public void setAnatomy(String anatomy) {
        this.anatomy = anatomy;
    }

    @Override
    public String toString() {
        return "Pinfo{" +
                "location='" + location + '\'' +
                ", treatment='" + treatment + '\'' +
                ", operation='" + operation + '\'' +
                ", anatomy='" + anatomy + '\'' +
                '}';
    }
}
