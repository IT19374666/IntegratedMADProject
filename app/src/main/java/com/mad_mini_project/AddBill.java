package com.mad_mini_project;

public class AddBill {

    private String bTitle,bAmount,bDescription,bId,bDate,selDate,btime;

    public AddBill(String bTitle, String bAmount, String bDescription, String bId, String bDate, String selDate,String btime) {
        this.bTitle = bTitle;
        this.bAmount = bAmount;
        this.bDescription = bDescription;
        this.bId = bId;
        this.bDate = bDate;
        this.selDate = selDate;
        this.btime = btime;
    }

    public AddBill() {
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getSelDate() {
        return selDate;
    }

    public void setSelDate(String selDate) {
        this.selDate = selDate;
    }

    public String getbTitle() {
        return bTitle;
    }

    public void setbTitle(String bTitle) {
        this.bTitle = bTitle;
    }

    public String getbAmount() {
        return bAmount;
    }

    public void setbAmount(String bAmount) {
        this.bAmount = bAmount;
    }

    public String getbDescription() {
        return bDescription;
    }

    public void setbDescription(String bDescription) {
        this.bDescription = bDescription;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }
}
