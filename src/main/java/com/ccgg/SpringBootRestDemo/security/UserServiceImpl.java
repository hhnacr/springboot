package com.ccgg.SpringBootRestDemo.security;

import com.ccgg.SpringBootRestDemo.beans.CcggUser;
import com.ccgg.SpringBootRestDemo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        CcggUser user = userDao.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        return user;
    }
}
