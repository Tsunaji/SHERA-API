package com.shera.sheraapi;

import javax.naming.ldap.LdapName;
import org.junit.Test;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;

public class LdapNameTest {

    @Test
    public void testLdapName() {
        LdapName ldapName = LdapUtils.newLdapName("ou=People, dc=mahaphant, dc=com");

        LdapName jirasak = LdapNameBuilder.newInstance(ldapName).add("userPrincipalName", "ji*").build();

        System.out.println("Ldap TEST jirasak: " + jirasak);

        String userPrincipalName = LdapUtils.getStringValue(jirasak, "userPrincipalName");

        System.out.println("Ldap TEST userPrincipalName: " + userPrincipalName);
    }
}
