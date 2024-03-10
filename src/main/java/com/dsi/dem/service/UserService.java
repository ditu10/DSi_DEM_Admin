package com.dsi.dem.service;

import com.dsi.dem.model.Employee;
import com.dsi.dem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public User addAdditionalInformationForUser(Employee employee, String password) {
        User user = new User();
        user.setEmail(employee.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles("USER");
        user.setEnabled(true);
        return user;
    }
}
