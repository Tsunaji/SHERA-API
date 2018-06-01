/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shera.sheraapi.service;

import com.shera.sheraapi.model.LdapUser;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
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
//    private static final String LDAP_SERVER_PORT = "3268";
    private static final String LDAP_BASE_DN = "dc=mahaphant,dc=com";
    private static final String LDAP_BIND_DN = "cn=sheraadm, cn=Users, dc=mahaphant, dc=com";
    private static final String LDAP_BIND_PASSWORD = "mhp@root1";

    public static final Logger logger = LogManager.getLogger(LdapUserServiceImpl.class);

    @Override
    public Hashtable<String, String> getLdapEnv() {
        Hashtable<String, String> env = new Hashtable<>();
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
    public DirContext getLdapContext(Hashtable<String, String> env) {
        DirContext ctx;
        try {
            ctx = new InitialDirContext(env);
            logger.info("Create context success!");
            return ctx;
        } catch (NamingException e) {
            logger.error("Create context error!: " + e.getMessage());
            return null;
        }
    }

    @Override
    public NamingEnumeration<SearchResult> getLdapSearchResultByUsername(DirContext ctx, String username) {
        try {
            NamingEnumeration<SearchResult> results;
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Search Entire Subtree
            controls.setCountLimit(1);   //Sets the maximum number of entries to be returned as a result of the search
            controls.setTimeLimit(5000); // Sets the time limit of these SearchControls in milliseconds

            String searchString = "(&(objectCategory=user)(sAMAccountName=" + username + "))";

            results = ctx.search("", searchString, controls);

            logger.info("Get LDAP search results success!");
            return results;
        } catch (NamingException e) {
            logger.error("Get LDAP search results error!: ", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean checkLdapUserWasNotLocked(String username) {
        Hashtable<String, String> env = getLdapEnv();
        DirContext ctx = getLdapContext(env);
        NamingEnumeration<SearchResult> results = getLdapSearchResultByUsername(ctx, username);
        try {
            if (results.hasMore()) {
                SearchResult result = (SearchResult) results.next();
                Attributes attrs = result.getAttributes();
                Attribute lockoutTimeAttr = attrs.get("lockoutTime");
                String lockoutTime = (String) lockoutTimeAttr.get();
                if (lockoutTime.equals("0")) {
                    logger.debug("lockoutTime = " + lockoutTime + ", User: " + username + " was not locked!");
                    return true;
                } else {
                    logger.debug("lockoutTime = " + lockoutTime + ", User: " + username + " was locked!");
                    return false;
                }
            } else {
                logger.warn("Ldap results no data!");
                return false;
            }
        } catch (NamingException e) {
            logger.error("Check locked user error!: " + e.getMessage(), e);
            return false;
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    logger.error("Close results error!: " + e.getMessage(), e);
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                    logger.error("Close context error!: " + e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public boolean ldapValidateLogin(String username, String password) {
        Hashtable<String, String> env = getLdapEnv();
        DirContext ctx = getLdapContext(env);
        NamingEnumeration<SearchResult> results = getLdapSearchResultByUsername(ctx, username);
        try {
            if (results.hasMore()) {
                SearchResult result = (SearchResult) results.next();
                Attributes attrs = result.getAttributes();
                Attribute dnAttr = attrs.get("distinguishedName");
                String dn = (String) dnAttr.get();

                // User Exists, Validate the Password
                env.put(Context.SECURITY_PRINCIPAL, dn);
                env.put(Context.SECURITY_CREDENTIALS, password);

                new InitialDirContext(env); // Exception will be thrown on Invalid case
                logger.info("User: " + username + " validate succress!");
                return true;
            } else {
                logger.info("Ldap results no data!");
                return false;
            }
        } catch (NamingException e) {
            logger.error("Validate login error!: " + e.getMessage());
            return false;
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    logger.error("Close results error!: " + e.getMessage(), e);
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                    logger.error("Close context error!: " + e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public LdapUser getLdapUserByUsername(String username) {
        Hashtable<String, String> env = getLdapEnv();
        DirContext ctx = getLdapContext(env);
        NamingEnumeration<SearchResult> results = getLdapSearchResultByUsername(ctx, username);
        LdapUser ldapUser = null;
        try {
            if (results.hasMore()) {
                SearchResult result = (SearchResult) results.next();
                ldapUser = new LdapUser(result);
                return ldapUser;
            } else {
                logger.info("Ldap results no data!");
                return null;
            }
        } catch (NamingException e) {
            logger.error("Get LDAP user error!: " + e.getMessage(), e);
            return null;
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    logger.error("Close results error!: " + e.getMessage(), e);
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                    logger.error("Close context error!: " + e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public ArrayList<LdapUser> getListLdapUserByUsername(String username) {
        Hashtable<String, String> env = getLdapEnv();
        DirContext ctx = getLdapContext(env);
        NamingEnumeration<SearchResult> results = getLdapSearchResultByUsername(ctx, username);
        ArrayList<LdapUser> ldapUsers = new ArrayList<>();
        try {
            while (results.hasMore()) {
                SearchResult result = (SearchResult) results.next();

                LdapUser ldapUser = new LdapUser(result);

                ldapUsers.add(ldapUser);
            }
            return ldapUsers;
        } catch (NamingException e) {
            logger.error("Get LDAP user error!: " + e.getMessage(), e);
            return null;
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    logger.error("Close results error!: " + e.getMessage(), e);
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                    logger.error("Close context error!: " + e.getMessage(), e);
                }
            }
        }
    }
}
