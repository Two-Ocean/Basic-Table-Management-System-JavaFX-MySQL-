package com.example.assessment2;

public class Apply {
    private int sID;
    private String itpName;
    private String major;
    private String decision;

    // Constructor
    public Apply(int sID, String itpName, String major, String decision) {
        this.sID = sID;
        this.itpName = itpName;
        this.major = major;
        this.decision = decision;
    }

    // Getters and Setters
    public int getsID() {
        return sID;
    }

    public void setsID(int sID) {
        this.sID = sID;
    }

    public String getItpName() {
        return itpName;
    }

    public void setItpName(String itpName) {
        this.itpName = itpName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
