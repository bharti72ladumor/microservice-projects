package com.bharti.gateway.security;

import com.bharti.common.paylod.MethodUtils;
import com.bharti.gateway.model.EnumForRole;
import com.bharti.gateway.model.User;
import com.bharti.gateway.model.UserRepository;
import com.bharti.gateway.payload.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(SignupRequest request) {

        User dbUser = userRepository.findByUsername(request.getUserName());
        if (!MethodUtils.isObjectisNullOrEmpty(dbUser)) {
            return "User Already exist.";
        }
        User user = new User();
        user.setUsername(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsActive(Boolean.TRUE);

        if (!MethodUtils.isListIsNullOrEmpty(request.getRoles())) {
            Set<EnumForRole> roles = new HashSet<>();
            for (String role : request.getRoles()) {
                if (role.equals("admin")) {
                    roles.add(EnumForRole.ADMIN);
                }
                if (role.equals("manager")) {
                    roles.add(EnumForRole.MANAGER);
                }
            }
            user.setRoles(roles);
        }
        userRepository.save(user);
        return "Added Successfully";
    }

    public String login(String username, String password) {
        User dbUser = userRepository.findByUsername(username);
        if (dbUser != null && passwordEncoder.matches(password, dbUser.getPassword())) {
            return jwtUtils.doGenerateToken(new HashMap<>(), dbUser.getUsername());
        }
        throw new RuntimeException("Invalid credentials");
    }
}
