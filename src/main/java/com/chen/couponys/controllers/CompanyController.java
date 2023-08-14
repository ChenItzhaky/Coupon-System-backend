package com.chen.couponys.controllers;

import com.chen.couponys.bins.Category;
import com.chen.couponys.bins.Company;
import com.chen.couponys.bins.Coupon;
import com.chen.couponys.bins.User;
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
    public Company getCompanyDetails(@RequestHeader UUID token) throws Exception {
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
    public List<Coupon> getCompanyCoupons(@RequestHeader UUID token) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        int comId = companyRepository.findByEmail(user.getEmail()).get(0).getId();
        return companyService.getCompanyCoupons(comId);
    }


    @GetMapping("/coupons/Price")
    public List<Coupon> getCompanyCoupons(@RequestHeader UUID token, @RequestParam double max) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        int comId = companyRepository.findByEmail(user.getEmail()).get(0).getId();
        return companyService.getCompanyCoupons(max, comId);
    }


    @GetMapping("/coupons/category")
    public List<Coupon> getCompanyCoupons(@RequestHeader UUID token, @RequestParam Category category) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        int comId = companyRepository.findByEmail(user.getEmail()).get(0).getId();
        return companyService.getCompanyCoupons(category, comId);
    }

    @PostMapping("/coupons/add")
    public void addCoupon(@RequestHeader UUID token, @RequestBody Coupon coupon) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        Company company = companyRepository.findByEmail(user.getEmail()).get(0);
        coupon.setCompany(company);
        System.out.println(coupon);
        companyService.addCoupon(coupon);

    }

    @PutMapping("/coupons/{id}")
    public void updateCoupon(@RequestHeader UUID token, @PathVariable int id, @RequestBody Coupon coupon) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        Coupon couponTest = couponRepository.findById(id).orElseThrow(() -> new CoupounSystemException(ErrMsg.INVALID_ACTION));
        Company company = companyRepository.findByEmail(user.getEmail()).get(0);
        if (!couponTest.getCompany().equals(company)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        coupon.setCompany(company);
        companyService.updateCoupon(id, coupon);
    }

    @DeleteMapping("/coupons/{id}")
    public void deleteCoupon(@RequestHeader UUID token, @PathVariable int id) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.COMPANY)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        User user = tokenService.userFromToken(token);
        Coupon couponTest = couponRepository.findById(id).orElseThrow(() -> new CoupounSystemException(ErrMsg.INVALID_ACTION));
        Company company = companyRepository.findByEmail(user.getEmail()).get(0);
        if (!couponTest.getCompany().equals(company)) {
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        companyService.deleteCoupon(id);

    }


}
