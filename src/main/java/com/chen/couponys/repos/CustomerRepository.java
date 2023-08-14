package com.chen.couponys.repos;

import com.chen.couponys.bins.Category;
import com.chen.couponys.bins.Coupon;
import com.chen.couponys.bins.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByFirstName(String name);

    List<Customer> findByEmail(String email);

    @Query(value = "select `id` from inspringwetrust.coupons\n" +
            "INNER JOIN inspringwetrust.customers_coupon_list\n" +
            "ON  inspringwetrust.coupons.id = inspringwetrust.customers_coupon_list.coupon_list_id\n" +
            "WHERE inspringwetrust.customers_coupon_list.customer_id=?\n" +
            "\t\t\t", nativeQuery = true)
    List<Integer> findCustomerCoupons(int customerId);


    @Query(value = "select `id` from inspringwetrust.coupons\n" +
            "INNER JOIN inspringwetrust.customers_coupon_list\n" +
            "ON  inspringwetrust.coupons.id = inspringwetrust.customers_coupon_list.coupon_list_id\n" +
            "WHERE inspringwetrust.customers_coupon_list.customer_id=? and  inspringwetrust.coupons.category =?", nativeQuery = true)
    List<Integer> findCustomerCoupons(int customerId, String category);

    @Query(value = "select `id` from inspringwetrust.coupons\n" +
            "INNER JOIN inspringwetrust.customers_coupon_list\n" +
            "ON  inspringwetrust.coupons.id = inspringwetrust.customers_coupon_list.coupon_list_id\n" +
            "WHERE inspringwetrust.customers_coupon_list.customer_id=? and  inspringwetrust.coupons.price <=?", nativeQuery = true)
    List<Integer> findCustomerCoupons(int customerId, double maxPrice);


    @Query(value = "delete  FROM inspringwetrust.customers_coupon_list where customer_id = ? and coupon_list_id = ?;", nativeQuery = true)
    void delPurchaseCoupon(int customerID, int couponID);


}
