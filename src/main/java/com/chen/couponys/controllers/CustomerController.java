package com.chen.couponys.controllers;

import com.chen.couponys.bins.*;
import com.chen.couponys.exceptions.CoupounSystemException;
import com.chen.couponys.exceptions.ErrMsg;
import com.chen.couponys.login.ClientsType;
import com.chen.couponys.repos.CustomerRepository;
import com.chen.couponys.security.TokenService;
import com.chen.couponys.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    TokenService tokenService;


    @GetMapping("/coupon")
    public List<Coupon> getCustomerCoupons(@RequestHeader("Authorization") UUID token) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.CUSTOMER)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        int customerId = customerRepository.findByEmail(user.getEmail()).get(0).getId();
        System.out.println(customerId);
        return customerService.getCustomerCoupons(customerId);
    }


    @GetMapping("/coupon/category")
    List<Coupon> getCustomerCoupons(@RequestHeader("Authorization") UUID token, @RequestParam Category category) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.CUSTOMER)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        int customerId = customerRepository.findByEmail(user.getEmail()).get(0).getId();
        return customerService.getCustomerCoupons(customerId, category);
    }


    @GetMapping("/coupon/price")
    public List<Coupon> getCustomerCoupons(@RequestHeader("Authorization") UUID token, @RequestParam double maxPrice) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.CUSTOMER)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        int customerId = customerRepository.findByEmail(user.getEmail()).get(0).getId();
        return customerService.getCustomerCoupons(customerId, maxPrice);
    }

    @GetMapping
    public Customer getCustomerDetails(@RequestHeader("Authorization") UUID token) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.CUSTOMER)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        System.out.println(user);
        int customerId = customerRepository.findByEmail(user.getEmail()).get(0).getId();
        return customerService.getCustomerDetails(customerId);
    }

    @PutMapping("/purchase/{couponId}")
    private void purchaseCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int couponId) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.CUSTOMER)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        int customerId = customerRepository.findByEmail(user.getEmail()).get(0).getId();
        customerService.purchaseCoupon(customerId, couponId);

    }
}
