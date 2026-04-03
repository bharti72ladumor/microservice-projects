package com.bharti.gateway.controller;

import com.bharti.gateway.model.User;
import com.bharti.gateway.payload.SignupRequest;
import com.bharti.gateway.security.AuthService;
import com.bharti.gateway.security.JwtUtils;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Key;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/employee")
    public String employeeAccess() {
        return  "Only Employee Can access";
    }

    @GetMapping("/manager")
    public String managerAccess() {
        return "Only Manager Can access";
    }
    @PostMapping("/register")
    public String register(@RequestBody @Valid SignupRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return authService.login(user.getUsername(), user.getPassword());
//        CustomUser authenticatedUser = authService.login(user.getUsername(), user.getPassword());
//        if (authenticatedUser != null) {
//            Map<String, Object> claims = new HashMap<>();
//            String token = jwtUtils.doGenerateToken(claims, user.getUsername());
//            Map<String, String> response = new HashMap<>();
//            response.put("token", token);
//            return response;
//        } else {
//            throw new RuntimeException("Invalid username or password");
//        }
    }
}
