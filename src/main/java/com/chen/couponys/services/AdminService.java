package com.chen.couponys.services;

import com.chen.couponys.bins.Company;
import com.chen.couponys.bins.Customer;
import com.chen.couponys.bins.User;
import com.chen.couponys.exceptions.CoupounSystemException;

import java.util.List;


public interface AdminService {
     boolean login(String email, String password);
    void addCompany(Company company) throws Exception;
    void updateCompany (int id, Company company) throws Exception;
    void deleteCompany(int id) throws Exception;
    void deleteUser(int id) throws CoupounSystemException;
    List<Company> getAllCompanies();
    Company getSingleCompany(int id) throws Exception;
    void addCustomer (Customer customer) throws Exception;
    void updateCustomer (int id,Customer customer) throws Exception;
    void deleteCustomer (int id) throws Exception;
    List<Customer> getAllCustomers();
    List<User> getAllUser();
    Customer getSingleCustomer(int id) throws Exception;
     Customer getCustomerByEmail(String email);
     void delExpiredCoupon();

}
