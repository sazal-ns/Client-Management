package com.rtsoftbd.siddiqui.clientmanagement.model;

/**
 * Created by RTsoftBD_Siddiqui on 2017-02-28.
 */

public class Credit {

    private int id, credit, debit, mixBalance, recentBalance, childId, totalCredit, totalPaid, totalCreditById,totalPaidById ;
    private String type, description, date, reportDate, name;
    public Credit() {
    }

    public Credit(int id, int credit, int debit, int mixBalance, int recentBalance, int childId, String type, String description, String date, String reportDate, String name) {
        this.id = id;
        this.credit = credit;
        this.debit = debit;
        this.mixBalance = mixBalance;
        this.recentBalance = recentBalance;
        this.childId = childId;
        this.type = type;
        this.description = description;
        this.date = date;
        this.reportDate = reportDate;
        this.name = name;
    }

    public int getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }

    public int getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(int totalPaid) {
        this.totalPaid = totalPaid;
    }

    public int getTotalCreditById() {
        return totalCreditById;
    }

    public void setTotalCreditById(int totalCreditById) {
        this.totalCreditById = totalCreditById;
    }

    public int getTotalPaidById() {
        return totalPaidById;
    }

    public void setTotalPaidById(int totalPaidById) {
        this.totalPaidById = totalPaidById;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }

    public int getMixBalance() {
        return mixBalance;
    }

    public void setMixBalance(int mixBalance) {
        this.mixBalance = mixBalance;
    }

    public int getRecentBalance() {
        return recentBalance;
    }

    public void setRecentBalance(int recentBalance) {
        this.recentBalance = recentBalance;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
