package com.example.racketreadyapplication;

import java.io.Serializable;

public class Membership implements Serializable {

    private String membershipName;
    private double membershipPrice;
    private boolean enableCompetitive;
    private boolean enableCourtBooking;
    private boolean enableTournamentCreation;
    private int duration;


    public Membership(){

    }

    public Membership(String membershipName, double membershipPrice, boolean enableCompetitive, boolean enableCourtBooking, boolean enableTournamentCreation, int duration) {
        this.membershipName = membershipName;
        this.membershipPrice = membershipPrice;
        this.enableCompetitive = enableCompetitive;
        this.enableCourtBooking = enableCourtBooking;
        this.enableTournamentCreation = enableTournamentCreation;
        this.duration = duration;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public double getMembershipPrice() {
        return membershipPrice;
    }

    public void setMembershipPrice(double membershipPrice) {
        this.membershipPrice = membershipPrice;
    }

    public boolean isEnableCompetitive() {
        return enableCompetitive;
    }

    public void setEnableCompetitive(boolean enableCompetitive) {
        this.enableCompetitive = enableCompetitive;
    }

    public boolean isEnableCourtBooking() {
        return enableCourtBooking;
    }

    public void setEnableCourtBooking(boolean enableCourtBooking) {
        this.enableCourtBooking = enableCourtBooking;
    }

    public boolean isEnableTournamentCreation() {
        return enableTournamentCreation;
    }

    public void setEnableTournamentCreation(boolean enableTournamentCreation) {
        this.enableTournamentCreation = enableTournamentCreation;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
