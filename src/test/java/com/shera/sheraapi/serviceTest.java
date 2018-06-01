/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shera.sheraapi;

import com.shera.sheraapi.service.LdapUserService;
import com.shera.sheraapi.service.LdapUserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author jirasak_ka
 */
public class serviceTest {

//    @Autowired
    LdapUserService ldapUserService;
    public static final Logger logger = LogManager.getLogger(serviceTest.class);

//    @Test
    public void ldapTest() {
        String username = "jirasak_ka";
        String password = "Tsunaji230425352";
        ldapUserService = new LdapUserServiceImpl();

        ldapUserService.checkLdapUserWasNotLocked(username);
        ldapUserService.ldapValidateLogin(username, password);
        ldapUserService.getLdapUserByUsername(username).toString();
    }

//    @Test
    public void testPerformSomeTask() throws Exception {
        Log4J2PropertiesConf log4J2PropertiesConf = new Log4J2PropertiesConf();
        log4J2PropertiesConf.performSomeTask();
    }
}
