package com.shera.sheraapi.model;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LdapUser {

    private String employeeID = "";
    private String sAMAccountName = "";
    private String displayName = "";
    private String cn = "";
    private String givenName = "";
    private String sn = "";
    private String description = "";
    private String department = "";
    private String mail = "";
    private String title = "";
    private String distinguishedName = "";
    private String lockoutTime = "";

    public static final Logger logger = LogManager.getLogger(LdapUser.class);

    public LdapUser() {

    }

    public LdapUser(String employeeID, String sAMAccountName, String displayName, String cn, String givenName, String sn, String description, String department, String mail, String title, String distinguishedName, String lockoutTime) {
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
        this.lockoutTime = lockoutTime;
    }

    public LdapUser(SearchResult result) {
        try {

            Attributes attrs = result.getAttributes();

            if (attrs.get("employeeID") != null) {
                Attribute employeeIDAttr = attrs.get("employeeID");
                this.employeeID = (String) employeeIDAttr.get();
            }

            if (attrs.get("sAMAccountName") != null) {
                Attribute sAMAccountNameAttr = attrs.get("sAMAccountName");
                this.sAMAccountName = (String) sAMAccountNameAttr.get();
            }

            if (attrs.get("displayName") != null) {
                Attribute displayNameAttr = attrs.get("displayName");
                this.displayName = (String) displayNameAttr.get();
            }

            if (attrs.get("cn") != null) {
                Attribute cnAttr = attrs.get("cn");
                this.cn = (String) cnAttr.get();
            }

            if (attrs.get("givenName") != null) {
                Attribute givenNameAttr = attrs.get("givenName");
                this.givenName = (String) givenNameAttr.get();
            }

            if (attrs.get("sn") != null) {
                Attribute snAttr = attrs.get("sn");
                this.sn = (String) snAttr.get();
            }

            if (attrs.get("description") != null) {
                Attribute descriptionAttr = attrs.get("description");
                this.description = (String) descriptionAttr.get();
            }

            if (attrs.get("department") != null) {
                Attribute departmentAttr = attrs.get("department");
                this.department = (String) departmentAttr.get();
            }

            if (attrs.get("mail") != null) {
                Attribute mailAttr = attrs.get("mail");
                this.mail = (String) mailAttr.get();
            }

            if (attrs.get("title") != null) {
                Attribute titleAttr = attrs.get("title");
                this.title = (String) titleAttr.get();
            }

            if (attrs.get("distinguishedName") != null) {
                Attribute dnAttr = attrs.get("distinguishedName");
                this.distinguishedName = (String) dnAttr.get();
            }

            if (attrs.get("lockoutTime") != null) {
                Attribute lockoutTimeAttr = attrs.get("lockoutTime");
                this.lockoutTime = (String) lockoutTimeAttr.get();
            }

        } catch (NamingException ex) {
            logger.error("Get LDAP object NamingException error!: " + ex.getMessage());
        } catch (NullPointerException ex) {
            logger.error("Get LDAP object NullPointerException error!: " + ex.getMessage());
        }
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

    public String getLockoutTime() {
        return lockoutTime;
    }

    public void setLockoutTime(String lockoutTime) {
        this.lockoutTime = lockoutTime;
    }

    public String toString() {
        return "{employeeID = '" + this.employeeID + "', sAMAccountName = '" + this.sAMAccountName
                + "', displayName = '" + this.displayName + "', cn = '" + this.cn
                + "', givenName = '" + this.givenName + "', sn = '" + this.sn
                + "', description = '" + this.description + "', department = '" + this.department
                + "', mail = '" + this.mail + "', title = '" + this.title
                + "', distinguishedName = '" + this.distinguishedName + "', lockoutTime = '" + this.lockoutTime + "'}";
    }
}
