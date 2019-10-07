package com.ccgg.SpringBootRestDemo.dao;

import com.ccgg.SpringBootRestDemo.beans.CcggUser;
import org.springframework.data.jpa.repository.JpaRepository;
//jpa 对于hibernate的封装，对于单表有优势，多个表（3张）以上慎用
public interface UserDao extends JpaRepository<CcggUser, Integer> {
    //不用定义任何方法，可以自己知道用什么方法
    CcggUser findByUsername(String username);
}
