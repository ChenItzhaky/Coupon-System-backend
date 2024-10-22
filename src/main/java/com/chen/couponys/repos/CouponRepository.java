package com.chen.couponys.repos;

import com.chen.couponys.bins.Category;
import com.chen.couponys.bins.Coupon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {


    List<Coupon> findByCompanyId(int id);

    List<Coupon> findByEndDateAfter(Date date);

    List<Coupon> findByCompanyIdAndPriceLessThan(int id, double maxPrice);
    List<Coupon> findByPriceLessThan( double maxPrice);
    List<Coupon> findByTitle(String title);

    List<Coupon> findByCompanyIdAndTitle(int id, String title);

    List<Coupon> findByCompanyIdAndCategory(int id, Category category);
    List<Coupon> findByCategory(Category category);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `inspringwetrust`.`customers_coupon_list` (`customer_id`, `coupon_list_id`) VALUES (?, ?);",nativeQuery = true)
    void purchaseCoupon(int id, int couponId) throws Exception;

    @Modifying
    @Transactional
    @Query(value = "delete  FROM inspringwetrust.customers_coupon_list where coupon_list_id = ?;", nativeQuery = true)
    void delCouponPurchase(int id);


}
