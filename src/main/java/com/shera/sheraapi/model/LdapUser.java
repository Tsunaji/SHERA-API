/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shera.sheraapi.model;

/**
 *
 * @author jirasak_ka
 */
public class LdapUser {

    private String employeeID;
    private String sAMAccountName;
    private String displayName;
    private String cn;
    private String givenName;
    private String sn;
    private String description;
    private String department;
    private String mail;
    private String title;
    private String distinguishedName;

    public LdapUser(String employeeID, String sAMAccountName, String displayName, String cn, String givenName, String sn, String description, String department, String mail, String title, String distinguishedName) {
        this.employeeID = employeeID;
        this.sAMAccountName = sAMAccountName;
        this.displayName = displayName;
        this.cn = cn;
        this.givenName = givenName;
        this.sn = sn;
        this.description = description;
        this.department = department;
        this.mail = mail;
        this.title = title;
        this.distinguishedName = distinguishedName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getsAMAccountName() {
        return sAMAccountName;
    }

    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

}
