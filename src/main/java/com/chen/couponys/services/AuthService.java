package com.chen.couponys.services;

import com.chen.couponys.bins.LogToken;
import com.chen.couponys.bins.User;
import com.chen.couponys.exceptions.CoupounSystemException;


import java.util.UUID;

public interface AuthService {
    void register( User user) throws CoupounSystemException;
    LogToken login(User user) throws CoupounSystemException;

}
