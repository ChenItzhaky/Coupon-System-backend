package com.chen.couponys.services;

import com.chen.couponys.repos.CompanyRepository;
import com.chen.couponys.repos.CouponRepository;
import com.chen.couponys.repos.CustomerRepository;
import com.chen.couponys.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientService {
    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired 
    protected UserRepository userRepository;


}
