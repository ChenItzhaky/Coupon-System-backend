package com.chen.couponys.services;

import com.chen.couponys.bins.Company;
import com.chen.couponys.bins.Coupon;
import com.chen.couponys.bins.Customer;
import com.chen.couponys.bins.User;
import com.chen.couponys.exceptions.CoupounSystemException;
import com.chen.couponys.exceptions.ErrMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl extends ClientService implements AdminService {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;


    @Override
    public boolean login(String email, String password) {
        return (email.equals("admin@admin.com") && password.equals("admin"));
    }

    @Override
    public void addCompany(Company company) throws Exception {
        int id = company.getId();
        if (!companyRepository.existsById(id)){
            throw new CoupounSystemException(ErrMsg.ID_ALREADY_EXIST);
        }
        companyRepository.save(company);
    }

    @Override
    public void updateCompany(int id, Company company) throws Exception {
        if (!companyRepository.existsById(id)){
            throw new CoupounSystemException(ErrMsg.ID_NOT_FOUND);
        }
        Company oldCompany = companyRepository.findById(id).orElseThrow();
        List<Coupon> couponList = oldCompany.getCoupons();
        company.setId(id);
        company.setCoupons(couponList);
        companyRepository.saveAndFlush(company);
    }

    @Override
    public void deleteCompany(int id) throws Exception {
        if (!companyRepository.existsById(id)){
            throw new CoupounSystemException(ErrMsg.ID_NOT_FOUND);
        }
        Company company = companyService.getCompanyDetails(id);
        if (!company.getCoupons().isEmpty()){
        for (Coupon c:company.getCoupons()) {
            couponRepository.delCouponPurchase(c.getId());
            couponRepository.deleteById(c.getId());
        }
        }
        User user = userService.findByEmail(company.getEmail());
        companyRepository.deleteById(id);
        userRepository.deleteById(user.getId());
    }

    @Override
    public void deleteUser(int id) throws CoupounSystemException {
        if (!userRepository.existsById(id)){
            throw new CoupounSystemException(ErrMsg.ID_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getSingleCompany(int id) throws Exception {
        return companyRepository.findById(id)
                .orElseThrow(()->new  CoupounSystemException(ErrMsg.ID_NOT_FOUND));
    }

    @Override
    public void addCustomer(Customer customer) throws Exception {
        int id = customer.getId();
        if (!customerRepository.existsById(id)){
            throw new CoupounSystemException(ErrMsg.ID_NOT_FOUND);
        }
        customerRepository.save(customer);

    }

    @Override
    public void updateCustomer(int id, Customer customer) throws Exception {
        if (!customerRepository.existsById(id)){
            throw new CoupounSystemException(ErrMsg.ID_NOT_FOUND);
        }
        Customer olsCustomer =customerRepository.findById(id).orElseThrow();
        List<Coupon> couponList = olsCustomer.getCouponList();
        customer.setId(id);
        customer.setCouponList(couponList);
        customerRepository.saveAndFlush(customer);
    }

    @Override
    public void deleteCustomer(int id) throws Exception {
        if (!customerRepository.existsById(id)){
            throw new CoupounSystemException(ErrMsg.ID_NOT_FOUND);
        }
        Customer customer = customerService.getCustomerDetails(id);
        if (!customer.getCouponList().isEmpty()){
            for (Coupon c:customer.getCouponList()) {
                couponRepository.delCouponPurchase(c.getId());
                couponRepository.deleteById(c.getId());
            }
        }
        User user = userService.findByEmail(customer.getEmail());
        customerRepository.deleteById(id);
        userRepository.deleteById(user.getId());

    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Customer getSingleCustomer(int id) throws Exception {
        return customerRepository.findById(id).orElseThrow(()->new CoupounSystemException(ErrMsg.ID_NOT_FOUND));
    }
    public Customer getCustomerByEmail(String email){
       return customerRepository.findByEmail(email).get(0);
    }

    @Override
    public void delExpiredCoupon() {
        List<Coupon> couponList = couponRepository.findByEndDateAfter(Date.valueOf(LocalDate.now()));
        couponList.forEach(coupon -> couponRepository.delete(coupon));
    }


}

