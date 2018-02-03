package com.zdx.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//这两个注解看一下
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //启用方法安全设置
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    //这个KEY的作用是？
    private static final String KEY = "zdx.blog.com";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //使用BCrypt加密
    }

    //认证是由 AuthenticationManager 来管理的，但是真正进行认证的是 AuthenticationManager 中定义的 AuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder); //设置密码加密方式
        return authenticationProvider;
    }

    /**
     * 自定义配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/css/**","/js/**", "/fonts/**", "/index").permitAll()   //都可以访问
                                .antMatchers("/h2-console/**").permitAll()  //都可以访问 //h2-console是什么
                                .antMatchers("/admins/**").hasRole("ADMIN") //需要相应的角色才可以访问
                //为什么要加and()才行？
                                .and()
                                .formLogin()    //基于Form表单登陆验证
                                .loginPage("/login").failureUrl("/login-error") //自定义登陆页面
                                .and().rememberMe().key(KEY)    //启用remember me
                                .and().exceptionHandling().accessDeniedPage("/403"); //处理异常，拒绝访问就重定向到403页面
        http.csrf().ignoringAntMatchers("/h2-console/**");  //禁用H2控制台的CSRF防护
        http.headers().frameOptions().sameOrigin(); //允许来自同意来源的H2控制台的请求
    }

    /**
     * 认证信息管理
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }
}
