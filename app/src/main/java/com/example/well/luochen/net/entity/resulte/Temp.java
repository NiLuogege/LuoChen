package com.example.well.luochen.net.entity.resulte;

/**
 * Created by Well on 2017/2/15.
 */

public class Temp {
    public Double temp;
    public Long measureTime;
    public int status;

    @Override
    public String toString() {
        return "Temp{" +
                "temp=" + temp +
                ", measureTime=" + measureTime +
                ", status=" + status +
                '}';
    }
}
