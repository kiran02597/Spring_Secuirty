package com.example.demo.Security;

import jdk.nashorn.internal.runtime.regexp.joni.ApplyCaseFoldArg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.demo.Security.ApplicationUserPermission.*;
import static com.example.demo.Security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name()) //role based authentication
//                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST,"/management/api/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                // .httpBasic();
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/courses",true);
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails saiUser = User
                                .builder()
                                .username("sai")
                                .password(passwordEncoder.encode("password"))
                              //  .roles(STUDENT.name())
                                .authorities(STUDENT.getGrantedAuthorities())
                                .build();
        UserDetails kiranUser = User
                .builder()
                .username("kiran")
                .password(passwordEncoder.encode("password123"))
                //.roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails tonyUser = User
                .builder()
                .username("tony")
                .password(passwordEncoder.encode("password1"))
               // .roles(ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();
        return  new InMemoryUserDetailsManager(
                saiUser,
                kiranUser,
                tonyUser
        );

    }
}
