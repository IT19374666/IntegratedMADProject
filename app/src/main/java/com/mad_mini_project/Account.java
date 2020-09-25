package com.mad_mini_project;

public class Account {


    String accName;
    String accType;
    Double balance;
    String key;

    public Account() {

    }

    public Account(String accName, String accType, Double balance){
        this.accName = accName;
        this.accType = accType;
        this.balance = balance;

    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
