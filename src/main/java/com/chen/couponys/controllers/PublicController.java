package com.chen.couponys.controllers;

import com.chen.couponys.bins.Category;
import com.chen.couponys.bins.Company;
import com.chen.couponys.bins.Coupon;
import com.chen.couponys.bins.Customer;
import com.chen.couponys.exceptions.CoupounSystemException;
import com.chen.couponys.exceptions.ErrMsg;
import com.chen.couponys.login.ClientsType;
import com.chen.couponys.repos.CouponRepository;
import com.chen.couponys.repos.CustomerRepository;
import com.chen.couponys.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/public")
@CrossOrigin
public class PublicController {
    @Autowired
    private CouponRepository couponRepository;
    @Autowired

    private CustomerRepository customerRepository;
    @Autowired

    private AdminService adminService;


    @GetMapping("/coupon")
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @GetMapping("/coupon/category")
    public List<Coupon> getAllCouponsByCategory(@RequestParam Category category) {
        return couponRepository.findByCategory(category);
    }

    @GetMapping("/coupon/price")
    public List<Coupon> getAllCouponsByPrice(@RequestParam double maxPrice) {
        return couponRepository.findByPriceLessThan(maxPrice);
    }

    @PostMapping("/customer/add")
    public void addCustomer(@RequestBody Customer customer) throws Exception {

        adminService.addCustomer(customer);
}




}
