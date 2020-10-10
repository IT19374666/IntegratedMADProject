package com.mad_mini_project;

public class Transactions {

    private String id;
    private String name;
    private String description;
    private Double amount;
    private String date;

    public Transactions(){

    }

    public Transactions(String name,String description,Double amount,String date){

        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }
    public String getName() {//
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
