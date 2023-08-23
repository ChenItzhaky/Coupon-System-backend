package com.chen.couponys.controllers;

import com.chen.couponys.bins.Category;
import com.chen.couponys.bins.Company;
import com.chen.couponys.bins.Coupon;
import com.chen.couponys.repos.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/public")
@CrossOrigin
public class PublicController {
    @Autowired
    private CouponRepository couponRepository;

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

}
