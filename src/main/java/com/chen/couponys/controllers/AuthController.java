package com.chen.couponys.controllers;



import com.chen.couponys.bins.LogToken;
import com.chen.couponys.bins.User;
import com.chen.couponys.exceptions.CoupounSystemException;
import com.chen.couponys.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody User user) throws CoupounSystemException {
        authService.register(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public LogToken login(@RequestBody User user) throws CoupounSystemException {

        return authService.login(user);
    }
}
