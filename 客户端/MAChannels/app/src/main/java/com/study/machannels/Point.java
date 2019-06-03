package com.study.machannels;

public class Point {
    public Integer pid;
    public String pname;
    public Integer mid;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Override
    public String toString() {
        return "Point{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", mid=" + mid +
                '}';
    }
}
