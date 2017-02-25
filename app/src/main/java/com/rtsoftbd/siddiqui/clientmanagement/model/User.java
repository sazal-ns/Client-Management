package com.rtsoftbd.siddiqui.clientmanagement.model;

/**
 * Created by RTsoftBD_Siddiqui on 2017-02-23.
 */

public class User {
    private String  name, email, mobile, created_at, updated_at, loginDate;
    private int     id, status, credit, debit, balance, permission;

    public User() {
    }

    public User(String name, String email, String mobile, int status, int credit, int debit, int balance, String loginDate) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.status = status;
        this.credit = credit;
        this.debit = debit;
        this.balance = balance;
        this.loginDate = loginDate;
    }

    public User(String name, String email, String mobile, String loginDate, int status, int credit, int debit, int balance, int permission, String created_at, String updated_at, int id) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.loginDate = loginDate;
        this.status = status;
        this.credit = credit;
        this.debit = debit;
        this.balance = balance;
        this.permission = permission;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getId() {
        return id;
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
