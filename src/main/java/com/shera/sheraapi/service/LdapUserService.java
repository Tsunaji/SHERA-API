/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shera.sheraapi.service;

import com.shera.sheraapi.model.LdapUser;
import java.util.Hashtable;

/**
 *
 * @author jirasak_ka
 */
public interface LdapUserService {

    Hashtable<String, String> getLdapAdminEnv();

    boolean ldapValidateLogin(String username, String password);

    LdapUser getLdapUserByUsername(String username);
}
