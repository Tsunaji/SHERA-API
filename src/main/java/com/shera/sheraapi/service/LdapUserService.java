/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shera.sheraapi.service;

import com.shera.sheraapi.model.LdapUser;
import java.util.Hashtable;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;

/**
 *
 * @author jirasak_ka
 */
public interface LdapUserService {

    void getLdapContext();

    SearchResult getLdapSearchResultByUsername(String username);

    boolean checkLdapUserIsNotLocked(String username);
    
    boolean ldapValidateLogin(String username, String password);
    
    LdapUser getLdapUserByUsername(String username);
}
