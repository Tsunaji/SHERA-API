/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shera.sheraapi;

import com.shera.sheraapi.model.LdapUser;
import com.shera.sheraapi.service.LdapUserService;
import com.shera.sheraapi.service.LdapUserServiceImpl;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author jirasak_ka
 */
public class serviceTest {

    @Autowired
    LdapUserService ldapUserService;
    public static final Logger logger = LogManager.getLogger(serviceTest.class);

    @Test
    public void ldapTest() {
        String username = "j*";
        String password = "Tsunaji230425352";
//        ldapUserService = new LdapUserServiceImpl();
//        Hashtable<String, String> env = ldapUserService.getLdapEnv();
//        DirContext ctx = ldapUserService.getLdapContext(env);
//        NamingEnumeration<SearchResult> results = ldapUserService.getLdapSearchResultByUsername(ctx, "jirasak_ka");

//        logger.info("checkLdapUserWasNotLocked: " + ldapUserService.checkLdapUserWasNotLocked(username));
//        logger.info("ldapValidateLogin: " + ldapUserService.ldapValidateLogin(username, password));
//        logger.info("getLdapUserByUsername: " + ldapUserService.getLdapUserByUsername(username).toString());
//        ArrayList<LdapUser> ldapUsers = ldapUserService.getListLdapUserByUsername(username);
//        for (int i = 0; i < ldapUsers.size(); i++) {
//            logger.info("Index " + i + " getListLdapUserByUsername:" + ldapUsers.get(i).toString());
//        }
    }

//    @Test
//    public void testPerformSomeTask() throws Exception {
//        Log4J2PropertiesConf log4J2PropertiesConf = new Log4J2PropertiesConf();
//        log4J2PropertiesConf.performSomeTask();
//    }
}
