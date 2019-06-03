package com.study.machannels;

public class Desease2Meridian {
    public String desease;
    public String meridian;

    public String getDesease() {
        return desease;
    }

    public void setDesease(String desease) {
        this.desease = desease;
    }

    public String getMeridian() {
        return meridian;
    }

    public void setMeridian(String meridian) {
        this.meridian = meridian;
    }

    @Override
    public String toString() {
        return "Desease2Meridian{" +
                "desease='" + desease + '\'' +
                ", meridian='" + meridian + '\'' +
                '}';
    }
}
