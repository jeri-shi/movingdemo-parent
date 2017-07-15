package com.shijin.learn.movingdemo.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 
 *
 */
@EnableZuulProxy
@SpringBootApplication
@EnableOAuth2Sso
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class App extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().disable();
    http.authorizeRequests()
      .antMatchers("/uaa/**").permitAll()
      .anyRequest().authenticated()
      .and()
      .csrf().disable();
  }

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
