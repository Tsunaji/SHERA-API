/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shera.sheraapi.service;

import com.shera.sheraapi.model.LdapUser;
import java.util.Hashtable;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author jirasak_ka
 */
@Service("ldapUserService")
public class LdapUserServiceImpl implements LdapUserService {

    private static final String LDAP_SERVER = "10.61.10.27";
    private static final String LDAP_SERVER_PORT = "389";
    private static final String LDAP_BASE_DN = "dc=mahaphant,dc=com";
    private static final String LDAP_BIND_DN = "CN=sheraadm, cn=Users, DC=mahaphant,DC=com";
    private static final String LDAP_BIND_PASSWORD = "mhp@root1";

    private static final Hashtable<String, String> env = new Hashtable<String, String>();

    public static final Logger logger = LoggerFactory.getLogger(LdapUserServiceImpl.class);

    @Override
    public Hashtable<String, String> getLdapAdminEnv() {

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://" + LDAP_SERVER + ":" + LDAP_SERVER_PORT + "/" + LDAP_BASE_DN);

        // To get rid of the PartialResultException when using Active Directory
        env.put(Context.REFERRAL, "follow");

        // Needed for the Bind (User Authorized to Query the LDAP server)
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, LDAP_BIND_DN);
        env.put(Context.SECURITY_CREDENTIALS, LDAP_BIND_PASSWORD);

        return env;
    }

    @Override
    public boolean ldapValidateLogin(String username, String password) {
        Hashtable<String, String> env = getLdapAdminEnv();

        DirContext ctx;
        try {
            ctx = new InitialDirContext(env);
            logger.info("ldapValidateLogin(): Ldap admin valid login");
        } catch (NamingException e) {
            logger.error("ldapValidateLogin(): Ldap admin invalid login");
            throw new RuntimeException(e);
        }

        NamingEnumeration<SearchResult> results = null;

        try {
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Search Entire Subtree
            controls.setCountLimit(1);   //Sets the maximum number of entries to be returned as a result of the search
            controls.setTimeLimit(5000); // Sets the time limit of these SearchControls in milliseconds

            String searchString = "(&(objectCategory=user)(sAMAccountName=" + username + "))";

            results = ctx.search("", searchString, controls);
            if (results.hasMore()) {

                SearchResult result = (SearchResult) results.next();
                Attributes attrs = result.getAttributes();
                Attribute dnAttr = attrs.get("distinguishedName");
                String dn = (String) dnAttr.get();

                // User Exists, Validate the Password
                env.put(Context.SECURITY_PRINCIPAL, dn);
                env.put(Context.SECURITY_CREDENTIALS, password);

                new InitialDirContext(env); // Exception will be thrown on Invalid case
                logger.info("ldapValidateLogin(): Ldap user " + username + " valid login");
                return true;
            } else {
                logger.info("ldapValidateLogin(): Ldap user " + username + " invalid login");
                return false;
            }
        } catch (AuthenticationException e) { // Invalid Login
            logger.error("ldapValidateLogin(): Ldap user " + username + " invalid login");
            return false;
        } catch (NameNotFoundException e) { // The base context was not found.
            logger.error("ldapValidateLogin(): The base context was not found: " + e.toString());
            return false;
        } catch (SizeLimitExceededException e) {
            throw new RuntimeException("LDAP Query Limit Exceeded, adjust the query to bring back less records", e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {

            if (results != null) {
                try {
                    results.close();
                } catch (NamingException e) {
                    logger.error("ldapValidateLogin(): Ldap authen results close error !: " + e.toString());
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    logger.error("ldapValidateLogin(): Ldap authen context close error !: " + e.toString());
                }
            }

        }
    }

    @Override
    public LdapUser getLdapUserByUsername(String username) {
        LdapUser ldapUser = new LdapUser();
        Hashtable<String, String> env = getLdapAdminEnv();

        DirContext ctx;
        try {
            ctx = new InitialDirContext(env);
            logger.info("getLdapUserByUsername(): Ldap admin valid login");
        } catch (NamingException e) {
            logger.error("getLdapUserByUsername(): Ldap admin invalid login");
            throw new RuntimeException(e);
        }

        NamingEnumeration<SearchResult> results = null;

        try {
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Search Entire Subtree
            controls.setCountLimit(1);   //Sets the maximum number of entries to be returned as a result of the search
            controls.setTimeLimit(5000); // Sets the time limit of these SearchControls in milliseconds

            String searchString = "(&(objectCategory=user)(sAMAccountName=" + username + "))";

            results = ctx.search("", searchString, controls);
            if (results.hasMore()) {

                SearchResult result = (SearchResult) results.next();

                ldapUser = new LdapUser(result);

                logger.info("getLdapUserByUsername(): found " + username);
                return ldapUser;
            } else {
                logger.info("getLdapUserByUsername(): Not found " + username);
                return ldapUser;
            }
        } catch (AuthenticationException e) { // Invalid Login
            logger.error("getLdapUserByUsername(): Ldap user " + username + " invalid login");
            return ldapUser;
        } catch (NameNotFoundException e) { // The base context was not found.
            logger.error("getLdapUserByUsername(): The base context was not found: " + e.toString());
            return ldapUser;
        } catch (SizeLimitExceededException e) {
            throw new RuntimeException("LDAP Query Limit Exceeded, adjust the query to bring back less records", e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {

            if (results != null) {
                try {
                    results.close();
                } catch (NamingException e) {
                    logger.error("getLdapUserByUsername(): Ldap authen results close error !: " + e.toString());
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    logger.error("getLdapUserByUsername(): Ldap authen context close error !: " + e.toString());
                }
            }

        }
    }
}
