/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shera.sheraapi;

import com.shera.sheraapi.service.LdapUserService;
import com.shera.sheraapi.service.LdapUserServiceImpl;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author jirasak_ka
 */
public class serviceTest {

    @Autowired
    LdapUserService ldapUserService;

    @Test
    public void ldapTest() {
        ldapUserService = new LdapUserServiceImpl();
//        System.out.println("checkLdapUserIsNotLocked: " + ldapUserService.checkLdapUserIsNotLocked("jirasak_ka"));
//        System.out.println("ldapValidateLogin: " + ldapUserService.ldapValidateLogin("jirasak_ka", "Tsunaji230425352"));
        System.out.println("getLdapUserByUsername: " + ldapUserService.getLdapUserByUsername("sheraadm").toString());
    }
    
//    @Test
//    public void testPerformSomeTask() throws Exception {
//        Log4J2PropertiesConf log4J2PropertiesConf = new Log4J2PropertiesConf();
//        log4J2PropertiesConf.performSomeTask();
//    }

}
