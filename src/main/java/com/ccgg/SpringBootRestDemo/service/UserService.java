package com.ccgg.SpringBootRestDemo.service;

import com.ccgg.SpringBootRestDemo.beans.CcggUser;
import com.ccgg.SpringBootRestDemo.beans.UserProfile;
import com.ccgg.SpringBootRestDemo.dao.UserDao;
import com.ccgg.SpringBootRestDemo.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.util.Password;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    public Response register(CcggUser user){
        user.setPassword(passwordEncoder.encode((user.getPassword())));//加密过后等级11
        List<UserProfile> profiles = new ArrayList<UserProfile>();
        profiles.add(new UserProfile((2)));//为什么设置2？role_user在sql里，表之间有join
        user.setProfiles(profiles);
        System.out.println(user);
        userDao.save(user);
        return new Response(true);

    }

    public Response deleteUser(int id) {
        if (userDao.findById(id) != null) {
            userDao.deleteById(id);
            return new Response(true);
        } else {
            return new Response(false, "User is not found!");
        }
    }

}
