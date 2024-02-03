package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean valid(User user) {
        boolean isValid = true;
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            isValid = false;
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            isValid = false;
        }
        if (user.getFullname() == null || user.getFullname().isEmpty()) {
            isValid = false;
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }
    public List<User> findAll() {return userRepository.findAll();}
    public User findById(Integer id) { return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not fount.")); }

    public User findByUsername(String username) { return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + "doesn't exist")); }
    public void deleteById(Integer id) {
        try {
            User user = this.findById(id);
            userRepository.delete(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("User not found. Cannot be deleted.");
        }
    }

    public User create(User user) {
        User res = null;
        if (valid(user)) {
            try {
                this.findByUsername(user.getUsername());
                throw new IllegalArgumentException("User already exists");
            } catch (UsernameNotFoundException e) {
                res = userRepository.save(user);
            }
        } else {
            throw new IllegalArgumentException("Invalid user");
        }
        return res;
    }

    public User update(Integer id, User user) {
        User res = null;
        if (valid(user)) {
            try {
                User oldUser = this.findById(id);
                oldUser.setFullname(user.getFullname());
                oldUser.setPassword(user.getPassword());
                oldUser.setRole(user.getRole());
                oldUser.setUsername(user.getUsername());
                res = userRepository.save(oldUser);
            } catch (Exception e) {
                throw new IllegalArgumentException("User not found. Cannot be updated.");
            }
        } else {
            throw new IllegalArgumentException("Invalid user");
        }
        return res;
    }

    /**
     * Validates the password according to the specified criteria.
     *
     * @param password the password to validate
     * @throws IllegalArgumentException if the password does not meet the specified criteria
     */
    public void passwordValid(String password) {
        if(!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new IllegalArgumentException("A password must contain at least 8 characters, one uppercase letter, one digit, and one special character");
        }
    }

    /**
     * Checks if the user associated with the provided SecurityContext is an admin.
     *
     * @param securityContext the SecurityContext object
     * @return true if the user is an admin, otherwise false
     */
    public boolean isAdmin(SecurityContext securityContext) {
        boolean isAdmin = false;
        UserDetails user = getUserDetailsFromSecurityContext(securityContext);
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            isAdmin = true;
        }
        return isAdmin;
    }

    /**
     * Verifies if the provided user ID matches the ID of the user associated with the provided SecurityContext.
     *
     * @param id the ID to verify
     * @param securityContext the SecurityContext object
     * @return true if the provided ID matches the ID of the user, otherwise false
     */
    public boolean idVerifier(Integer id, SecurityContext securityContext) {
        UserDetails user = getUserDetailsFromSecurityContext(securityContext);
        Integer _id = this.findByUsername(user.getUsername()).getId();
        return _id.equals(id);
    }

    /**
     * Retrieves UserDetails from the SecurityContext.
     *
     * @param securityContext the SecurityContext object
     * @return UserDetails object retrieved from the SecurityContext
     */
    public UserDetails getUserDetailsFromSecurityContext(SecurityContext securityContext) {
        return ((UserDetails) securityContext.getAuthentication().getPrincipal());
    }
}
