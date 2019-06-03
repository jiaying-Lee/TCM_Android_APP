package com.study.machannels;

public class Phenotype {
    public Integer pid;
    public String description;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Phenotype{" +
                "pid=" + pid +
                ", description='" + description + '\'' +
                '}';
    }
}
