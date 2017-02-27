package com.rtsoftbd.siddiqui.clientmanagement.model;

/**
 * Created by RTsoftBD_Siddiqui on 2017-02-23.
 */

public class User {
    private static String  name, email, mobile, created_at, updated_at, loginDate, description;
    private static int     id, status, credit, debit, balance, permission;

    public User() {
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getMobile() {
        return mobile;
    }

    public static void setMobile(String mobile) {
        User.mobile = mobile;
    }

    public static String getCreated_at() {
        return created_at;
    }

    public static void setCreated_at(String created_at) {
        User.created_at = created_at;
    }

    public static String getUpdated_at() {
        return updated_at;
    }

    public static void setUpdated_at(String updated_at) {
        User.updated_at = updated_at;
    }

    public static String getLoginDate() {
        return loginDate;
    }

    public static void setLoginDate(String loginDate) {
        User.loginDate = loginDate;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        User.description = description;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        User.id = id;
    }

    public static int getStatus() {
        return status;
    }

    public static void setStatus(int status) {
        User.status = status;
    }

    public static int getCredit() {
        return credit;
    }

    public static void setCredit(int credit) {
        User.credit = credit;
    }

    public static int getDebit() {
        return debit;
    }

    public static void setDebit(int debit) {
        User.debit = debit;
    }

    public static int getBalance() {
        return balance;
    }

    public static void setBalance(int balance) {
        User.balance = balance;
    }

    public static int getPermission() {
        return permission;
    }

    public static void setPermission(int permission) {
        User.permission = permission;
    }
}
