package com.example.demo.auth;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.Security.ApplicationUserRole.*;
@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }
    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUserList = Lists.newArrayList(
            new ApplicationUser("kumar",
                                passwordEncoder.encode("password"),
                                STUDENT.getGrantedAuthorities(),
                                true,
                                true,
                                true,
                                 true
        ),
        new ApplicationUser("kiran",
                passwordEncoder.encode("password123"),
                ADMIN.getGrantedAuthorities(),
                true,
                true,
                true,
                true
        ),
        new ApplicationUser("sai",
                passwordEncoder.encode("password1"),
                ADMINTRAINEE.getGrantedAuthorities(),
                true,
                true,
                true,
                true
        )
        );
        return applicationUserList;
    }
}
