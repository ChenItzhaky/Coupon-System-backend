package com.chen.couponys.controllers;

import com.chen.couponys.bins.*;
import com.chen.couponys.exceptions.CoupounSystemException;
import com.chen.couponys.exceptions.ErrMsg;
import com.chen.couponys.login.ClientsType;
import com.chen.couponys.repos.CompanyRepository;
import com.chen.couponys.repos.CouponRepository;
import com.chen.couponys.security.TokenService;
import com.chen.couponys.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/company")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    private TokenService tokenService;

    @GetMapping
    public Company getCompanyDetails(@RequestHeader("Authorization") UUID token) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        System.out.println(user);
        int comId = companyRepository.findByEmail(user.getEmail()).get(0).getId();
        System.out.println(comId);
        return companyService.getCompanyDetails(comId);
    }

    @GetMapping("/coupons")
    public List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        int comId = companyRepository.findByEmail(user.getEmail()).get(0).getId();
        return companyService.getCompanyCoupons(comId);
    }


    @GetMapping("/coupons/Price")
    public List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token, @RequestParam double max) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        int comId = companyRepository.findByEmail(user.getEmail()).get(0).getId();
        return companyService.getCompanyCoupons(max, comId);
    }


    @GetMapping("/coupons/category")
    public List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token, @RequestParam Category category) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        int comId = companyRepository.findByEmail(user.getEmail()).get(0).getId();
        return companyService.getCompanyCoupons(category, comId);
    }

    @PostMapping("/coupons/add")
    public void addCoupon(@RequestHeader("Authorization") UUID token, @RequestBody Coupon coupon) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        Company company = companyRepository.findByEmail(user.getEmail()).get(0);
        coupon.setCompany(company);
        System.out.println(coupon);
        companyService.addCoupon(coupon);

    }

    @PutMapping("/coupons/{couponId}")
    public void updateCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int couponId, @RequestBody Coupon coupon) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        Coupon couponTest = couponRepository.findById(couponId).orElseThrow(() -> new CoupounSystemException(ErrMsg.INVALID_ACTION));
        Company company = companyRepository.findByEmail(user.getEmail()).get(0);
        if (!couponTest.getCompany().equals(company)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        coupon.setCompany(company);
        companyService.updateCoupon(couponId, coupon);
    }

    @DeleteMapping("/coupons/{couponId}")
    public void deleteCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int couponId) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        Coupon couponTest = couponRepository.findById(couponId).orElseThrow(() -> new CoupounSystemException(ErrMsg.INVALID_ACTION));
        Company company = companyRepository.findByEmail(user.getEmail()).get(0);
        if (!couponTest.getCompany().equals(company)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        companyService.deleteCoupon(couponId);

    }


}
