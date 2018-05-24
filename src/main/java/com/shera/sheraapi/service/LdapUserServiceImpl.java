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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private Hashtable<String, String> env;
    private DirContext ctx;
    private NamingEnumeration<SearchResult> results;

    public static final Logger logger = LogManager.getLogger(LdapUserServiceImpl.class);

    @Override
    public void getLdapContext() {

        env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://" + LDAP_SERVER + ":" + LDAP_SERVER_PORT + "/" + LDAP_BASE_DN);

        // To get rid of the PartialResultException when using Active Directory
        env.put(Context.REFERRAL, "follow");

        // Needed for the Bind (User Authorized to Query the LDAP server)
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, LDAP_BIND_DN);
        env.put(Context.SECURITY_CREDENTIALS, LDAP_BIND_PASSWORD);

        try {
            ctx = new InitialDirContext(env);
            logger.info("Ldap context was created");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public SearchResult getLdapSearchResultByUsername(String username) {
        getLdapContext();
        SearchResult result;
        try {
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Search Entire Subtree
            controls.setCountLimit(1);   //Sets the maximum number of entries to be returned as a result of the search
            controls.setTimeLimit(5000); // Sets the time limit of these SearchControls in milliseconds

            String searchString = "(&(objectCategory=user)(sAMAccountName=" + username + "))";

            results = ctx.search("", searchString, controls);
            if (results.hasMore()) {
                result = (SearchResult) results.next();
                logger.info("Found " + username + " LDAP result");
                return result;
            } else {
                logger.info("Not found " + username + " LDAP result");
                return null;
            }
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean checkLdapUserIsNotLocked(String username) {
        SearchResult result = (SearchResult) getLdapSearchResultByUsername(username);
        Attributes attrs = result.getAttributes();
        Attribute lockoutTimeAttr = attrs.get("lockoutTime");
        try {
            String lockoutTime = (String) lockoutTimeAttr.get();
            logger.debug("Username: " + username + " is not locked ? = " + lockoutTime.equals("0"));
            return lockoutTime.equals("0");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return false;
    }

    @Override
    public boolean ldapValidateLogin(String username, String password) {
        SearchResult result = (SearchResult) getLdapSearchResultByUsername(username);
        try {
            Attributes attrs = result.getAttributes();
            Attribute dnAttr = attrs.get("distinguishedName");
            String dn = (String) dnAttr.get();

            // User Exists, Validate the Password
            env.put(Context.SECURITY_PRINCIPAL, dn);
            env.put(Context.SECURITY_CREDENTIALS, password);

            new InitialDirContext(env); // Exception will be thrown on Invalid case
            logger.info("Username: " + username + " is valid");
            return true;
        } catch (AuthenticationException e) { // Invalid Login
            logger.error("Invalid Login: " + e.getMessage());
            return false;
        } catch (NameNotFoundException e) { // The base context was not found.
            logger.error("The base context was not found: ", e);
            return false;
        } catch (SizeLimitExceededException e) {
            logger.error("LDAP Query Limit Exceeded, adjust the query to bring back less records", e);
            return false;
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public LdapUser getLdapUserByUsername(String username) {
        SearchResult result = (SearchResult) getLdapSearchResultByUsername(username);
        LdapUser ldapUser = new LdapUser(result);
        logger.debug(ldapUser.toString());
        return ldapUser;
    }
}
