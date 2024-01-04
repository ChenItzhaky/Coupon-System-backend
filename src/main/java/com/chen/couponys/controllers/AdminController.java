package com.chen.couponys.controllers;

import com.chen.couponys.bins.*;
import com.chen.couponys.exceptions.CoupounSystemException;
import com.chen.couponys.exceptions.ErrMsg;
import com.chen.couponys.login.ClientsType;
import com.chen.couponys.security.TokenService;
import com.chen.couponys.services.AdminService;
import com.chen.couponys.services.CustomerService;
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
    @Autowired
    CustomerService customerService;

    @GetMapping("/companies")
    public List<Company> getAllCompanies(@RequestHeader("Authorization") UUID token) throws CoupounSystemException {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
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

    @DeleteMapping("/companies/{companyId}")
    public void deleteCompany( @RequestHeader("Authorization") UUID token, @PathVariable int companyId) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        adminService.deleteCompany(companyId);

    }

    @GetMapping("/companies/{companyId}")
    public Company getSingleCompany( @RequestHeader("Authorization") UUID token, @PathVariable int companyId) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        return adminService.getSingleCompany(companyId);
    }

    @PutMapping("/companies/{companyId}")
    public void updateCompany(@RequestHeader("Authorization") UUID token, @PathVariable int companyId, @RequestBody Company company) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        adminService.updateCompany(companyId,company);

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

    @DeleteMapping("/customer/{customerId}")
    public void deleteCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int customerId) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        adminService.deleteCustomer(customerId);

    }

    @GetMapping("/customer/{customerId}")
    public Customer getSingleCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int customerId) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        return adminService.getSingleCustomer(customerId);
    }

    @PutMapping("/customer/{customerId}")
    public void updateCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int customerId, @RequestBody Customer customer) throws Exception {
        if (!tokenService.isUserAllowed(token, ClientsType.ADMINISTRATOR)){
            throw new CoupounSystemException(ErrMsg.INVALID_ACTION);
        }
        adminService.updateCustomer(customerId, customer);

    }


}
