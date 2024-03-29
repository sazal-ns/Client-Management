package com.rtsoftbd.siddiqui.clientmanagement.model;

/**
 * Created by RTsoftBD_Siddiqui on 2017-03-01.
 */

public class AllUser {

    private  String  name, email, mobile, created_at, updated_at, loginDate, description;
    private  int     id, status, credit, debit, balance, permission;

    public AllUser() {
    }

    public AllUser(String name, String email, String mobile, String created_at, String updated_at, String loginDate, String description, int id, int status, int credit, int debit, int balance, int permission) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.loginDate = loginDate;
        this.description = description;
        this.id = id;
        this.status = status;
        this.credit = credit;
        this.debit = debit;
        this.balance = balance;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}
