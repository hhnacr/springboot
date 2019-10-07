package com.ccgg.SpringBootRestDemo.security;

import com.ccgg.SpringBootRestDemo.handler.*;
import jdk.internal.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AccessDeniedHandlerImpl accessDeniedHandlerImpl;
    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPointImpl;
    @Autowired
    AuthenticationFailureHandlerImpl authenticationFailureHandlerImpl;
    @Autowired
    AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;
    @Autowired
    LogoutSuccessHandlerImpl logoutSuccessHandlerImpl;
    @Autowired
    UserServiceImpl userDetailsService;//UserDetailsService userDetailsService；

    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        //权限管理
        http.csrf().disable().cors().and().authorizeRequests()//and 是一个连接符；验证请求
                .antMatchers("/products/*").hasRole("ADMIN")//任何权限都能访问
                //cors config 若前台和后台不是相同的域，自动block掉后台的requst，请求会被拦截，用cors配置一下端口。
                //xss；csrf；jsonp也能解决跨域问题，但是比较古老。
                //.permitAll()
                //.hasAnyRole("ROLE_ADMIN")//或者这么写，不同形式
                .and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointImpl)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandlerImpl)
                .and()
                //下面要写登录
        .formLogin()
                .permitAll()
                .loginProcessingUrl("/login")//用的是form data
                .successHandler(authenticationSuccessHandlerImpl)//会在login后调用这个handler
                .failureHandler(authenticationFailureHandlerImpl)
                .usernameParameter("username").passwordParameter("password")//用这俩个东西登录
                .and()
                //logout
        .logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandlerImpl)
                .and()
                .rememberMe();//记住密码一段时间，接下来要配置一下cors

    }
    //cors support
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");//允许任何访问
        //you should
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","HEAD","OPTIONS"));
        configuration.addAllowedHeader("*");//包含请求编码格式等
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder(11);
        return encoder;
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }




}
