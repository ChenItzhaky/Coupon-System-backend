package com.chen.couponys.services;

import com.chen.couponys.bins.Category;
import com.chen.couponys.bins.Coupon;
import com.chen.couponys.bins.Customer;
import com.chen.couponys.exceptions.CoupounSystemException;
import com.chen.couponys.exceptions.ErrMsg;
import com.chen.couponys.repos.CouponRepository;
import com.chen.couponys.repos.CustomerRepository;
import com.chen.couponys.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service

public class CustomerServiceImpl extends ClientService implements CustomerService {


    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public void purchaseCoupon(int customerId, int couponId) throws Exception {
        System.out.println(customerId + " ----  " + couponId);
        List<Coupon> couponList = getCustomerDetails(customerId).getCouponList();
        System.out.println(couponList);
        for (Coupon coupon : couponList) {
            if (coupon.getId() == couponId) {
                throw new CoupounSystemException(ErrMsg.COUPON_ALREADY_PURCHASED);
            }
        }
        Coupon coupon1 = couponRepository.findById(couponId)
                .orElseThrow(()->new CoupounSystemException(ErrMsg.ID_NOT_FOUND));
        if (coupon1.getAmount() == 0) {
            throw new CoupounSystemException(ErrMsg.COUPON_SOLD_OUT);
        }
        coupon1.setAmount(coupon1.getAmount() - 1);
        couponRepository.saveAndFlush(coupon1);
        couponRepository.purchaseCoupon(customerId, couponId);

    }

    @Override
    public void delPurchaseCoupon(int customerId, int couponId) {
        customerRepository.delPurchaseCoupon(customerId,couponId);
    }

    // TODO: 02/08/2023 I will fix that some day
    @Override
    public List<Coupon> getCustomerCoupons(int customerId) throws CoupounSystemException {
        List<Integer> couponsId =customerRepository.findCustomerCoupons(customerId);
        List<Coupon> couponList= new ArrayList<>();
        for (Integer id:couponsId) {
            couponList.add(couponRepository.findById(id)
                    .orElseThrow(()->new CoupounSystemException(ErrMsg.ID_NOT_FOUND)));
        }
        return couponList;
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerId, Category category) throws CoupounSystemException {
        List<Integer> couponsId =customerRepository.findCustomerCoupons(customerId,category.name());
        System.out.println(couponsId);
        List<Coupon> couponList= new ArrayList<>();
        System.out.println(couponList);
        for (Integer i:couponsId) {
            couponList.add(couponRepository.findById(i)
                    .orElseThrow(()->new CoupounSystemException(ErrMsg.ID_NOT_FOUND)));
        }
        return couponList;
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerId, double maxPrice) throws CoupounSystemException {
        List<Integer> couponsId =customerRepository.findCustomerCoupons(customerId,maxPrice);
        List<Coupon> couponList= new ArrayList<>();
        for (Integer i:couponsId) {
            couponList.add(couponRepository.findById(i)
                    .orElseThrow(()->new CoupounSystemException(ErrMsg.ID_NOT_FOUND)));
        }
        return couponList;
    }

    @Override
    public Customer getCustomerDetails(int customerId) throws Exception {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CoupounSystemException(ErrMsg.ID_NOT_FOUND));
    }


}
