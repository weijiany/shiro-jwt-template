package com.example.demo.config;

import com.example.demo.service.AccountService;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;

@Configuration
public class ShiroConfig {

    private final AccountService accountService;

    public ShiroConfig(AccountService accountService) {
        this.accountService = accountService;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        filterFactory.setSecurityManager(securityManager);

        HashMap<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JWTFilter());
        filterFactory.setFilters(filterMap);


        HashMap<String, String> map = new HashMap<>();
        map.put("/login", "anon");
        map.put("/**", "jwt");
        filterFactory.setFilterChainDefinitionMap(map);
        return filterFactory;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        return securityManager;
    }

    @Bean
    public Realm realm() {
        return new MyRealm(accountService);
    }
}
