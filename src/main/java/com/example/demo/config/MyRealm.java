package com.example.demo.config;

import com.auth0.jwt.JWT;
import com.example.demo.exception.UnAuthenticationException;
import com.example.demo.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collections;

public class MyRealm extends AuthorizingRealm {

    private final AccountService accountService;

    public MyRealm(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String jwtToken = token.getPrincipal().toString();
        String username = JWT.decode(jwtToken).getClaim("username").asString();
        if (!accountService.checkLoginAndExpireToken(username, jwtToken))
            throw new UnAuthenticationException("un authentication");

        return new SimpleAuthenticationInfo(jwtToken, "", getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String role = JWT.decode(token).getClaim("role").asString();
        return new SimpleAuthorizationInfo(Collections.singleton(role));
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
}
