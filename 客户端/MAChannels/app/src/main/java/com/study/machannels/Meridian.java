package com.study.machannels;

public class Meridian {
    public Integer mid;
    public String mname;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    @Override
    public String toString() {
        return "Meridian{" +
                "mid=" + mid +
                ", mname='" + mname + '\'' +
                '}';
    }
}
