package com.mad_mini_project;

public class Envelope {
    private String name;
    private Double amount;
    String month;
    String envkey;

    public Envelope() {
    }

    public Envelope(String name, Double amount, String month) {
        this.name = name;
        this.amount = amount;
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEnvkey() {
        return envkey;
    }

    public void setEnvkey(String envkey) {
        this.envkey = envkey;
    }
}
