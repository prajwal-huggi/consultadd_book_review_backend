package com.example.consultadd_mini_project.service;

import com.example.consultadd_mini_project.Repository.UserRepo;
import com.example.consultadd_mini_project.model.User;
import com.example.consultadd_mini_project.model.principal.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= repo.findByEmail(email);
        System.out.println("Employee in db is "+ user);

        if(user== null){
            System.out.println("User not found in MyUserDetailsService");
            throw new UsernameNotFoundException("User not found");
        }

         return new UserPrincipal(user);
    }
}
