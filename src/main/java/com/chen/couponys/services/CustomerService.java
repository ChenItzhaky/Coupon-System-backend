package com.chen.couponys.services;

import com.chen.couponys.bins.Category;
import com.chen.couponys.bins.Coupon;
import com.chen.couponys.bins.Customer;
import com.chen.couponys.exceptions.CoupounSystemException;

import java.util.List;

public interface CustomerService {
    ;

    List<Coupon> getCustomerCoupons(int customerId) throws CoupounSystemException;

    List<Coupon> getCustomerCoupons(int id, Category category) throws CoupounSystemException;

    List<Coupon> getCustomerCoupons(int id ,double maxPrice) throws CoupounSystemException;

    Customer getCustomerDetails(int id) throws Exception;

    void purchaseCoupon(int id, int couponId) throws Exception;
    void delPurchaseCoupon(int customerId, int couponId);

}
