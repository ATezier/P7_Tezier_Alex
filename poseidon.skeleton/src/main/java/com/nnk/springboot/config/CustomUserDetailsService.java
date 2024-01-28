package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAutorities(user.getRole()));
    }

    private List<GrantedAuthority> getGrantedAutorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if(role.contains(",")) {
            String[] roles = role.split(",");
            for (String _role : roles) {
                authorities.add(new SimpleGrantedAuthority("Role_" + _role));
            }
        } else {
            authorities.add(new SimpleGrantedAuthority("Role_" + role));
        }
        return authorities;
    }
}
