package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {
    public final ApplicationUserDao applicationuserdao;
@Autowired
    public ApplicationUserService(ApplicationUserDao applicationuserdao) {
        this.applicationuserdao = applicationuserdao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationuserdao.selectApplicationUserByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("username %s not found", username)));
    }
}
