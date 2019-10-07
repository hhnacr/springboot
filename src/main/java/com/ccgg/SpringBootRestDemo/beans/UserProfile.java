package com.ccgg.SpringBootRestDemo.beans;
//这个文件用做记录user权限管理
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ccgg_user_profile")
public class UserProfile implements GrantedAuthority {
    private static final long serialVersionUID = 1L;
    @Id
    private int id;
    @Column
    private  String type;

    @Override
    public String getAuthority() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserProfile(){
        super();
    }
    public UserProfile(int id) {
        super();
        this.id = id;
    }

    public UserProfile(String type) {
        super();
        this.type = type;
    }

    public UserProfile(int id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
