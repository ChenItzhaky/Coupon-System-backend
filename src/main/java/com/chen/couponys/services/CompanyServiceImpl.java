package com.chen.couponys.services;

import com.chen.couponys.bins.Category;
import com.chen.couponys.bins.Company;
import com.chen.couponys.bins.Coupon;
import com.chen.couponys.exceptions.CoupounSystemException;
import com.chen.couponys.exceptions.ErrMsg;
import com.chen.couponys.repos.CompanyRepository;
import com.chen.couponys.repos.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service

public class CompanyServiceImpl extends ClientService implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CouponRepository couponRepository;



    @Override
    public void addCoupon(Coupon coupon) throws Exception {

        couponRepository.save(coupon);
    }

    @Override
    public void updateCoupon(int couponId, Coupon coupon) throws Exception {
        if (!couponRepository.existsById(couponId)) {
            throw new CoupounSystemException(ErrMsg.ID_NOT_FOUND);
        }
        coupon.setId(couponId);
        couponRepository.saveAndFlush(coupon);
    }

    @Override
    public void
    deleteCoupon(int couponId) throws CoupounSystemException {
        if (!couponRepository.existsById(couponId)) {
            throw new CoupounSystemException(ErrMsg.ID_NOT_FOUND);
        }
        couponRepository.deleteById(couponId);
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyId) {

        return couponRepository.findByCompanyId(companyId);
    }

    @Override
    public List<Coupon> getCompanyCoupons(double maxPrice, int companyId) {
        return couponRepository.findByCompanyIdAndPriceLessThan(companyId, maxPrice);
    }

    @Override
    public List<Coupon> getCompanyCoupons(Category category, int companyId) {
        return couponRepository.findByCompanyIdAndCategory(companyId, category);
    }

    @Override
    public Company getCompanyDetails(int companyId) throws Exception {
        return companyRepository.findById(companyId)
                .orElseThrow(()-> new CoupounSystemException(ErrMsg.ID_NOT_FOUND));
    }
}
