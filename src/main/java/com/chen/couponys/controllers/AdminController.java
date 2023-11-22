package com.chen.couponys.controllers;

import com.chen.couponys.bins.Company;
import com.chen.couponys.bins.Customer;
import com.chen.couponys.bins.LogToken;
import com.chen.couponys.bins.User;
import com.chen.couponys.exceptions.CoupounSystemException;
import com.chen.couponys.exceptions.ErrMsg;
import com.chen.couponys.login.ClientsType;
import com.chen.couponys.security.TokenService;
import com.chen.couponys.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    TokenService tokenService;

    @GetMapping("/companies")
    public List<Company> getAllCompanies(@RequestHeader("Authorization") UUID token) throws CoupounSystemException {
        System.out.println("1");
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            System.out.println("2");
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        System.out.println("3");
        return adminService.getAllCompanies();
    }

    @PostMapping("/companies/add")
    public void addCompany( @RequestHeader("Authorization") UUID token, @RequestBody Company company) throws Exception {
        System.out.println(company);
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        adminService.addCompany(company);

    }

    @DeleteMapping("/companies/{id}")
    public void deleteCompany( @RequestHeader("Authorization") UUID token, @PathVariable int id) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        adminService.deleteCompany(id);

    }

    @GetMapping("/companies/{id}")
    public Company getSingleCompany( @RequestHeader("Authorization") UUID token, @PathVariable int id) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        return adminService.getSingleCompany(id);
    }

    @PutMapping("/companies/{id}")
    public void updateCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id, @RequestBody Company company) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        adminService.updateCompany(id,company);

    }

    @GetMapping("/customer")
    public List<Customer> getAllCustomers(@RequestHeader("Authorization") UUID token) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        return adminService.getAllCustomers();
    }
    @GetMapping("/user")
    public List<User> getAllUser(@RequestHeader ("Authorization") UUID token) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        return adminService.getAllUser();
    }

    @PostMapping("/customer/add")
    public void addCustomer(@RequestHeader("Authorization") UUID token, @RequestBody Customer customer) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        adminService.addCustomer(customer);

    }

    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        adminService.deleteCustomer(id);

    }

    @GetMapping("/customer/{id}")
    public Customer getSingleCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        return adminService.getSingleCustomer(id);
    }

    @PutMapping("/customer/{id}")
    public void updateCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id, @RequestBody Customer customer) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        adminService.updateCustomer(id, customer);

    }


}
