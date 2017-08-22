package com.shijin.learn.movingdemo.apigateway;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 *
 */
@EnableZuulProxy
@SpringBootApplication
@EnableOAuth2Sso
@Controller
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class App extends WebSecurityConfigurerAdapter {
  private final static Logger LOGGER = LogManager.getLogger(App.class);

  @RequestMapping("/")
  @ResponseBody
  public String home(Principal principal) {
    LOGGER.trace("Hello " + principal);
    return "Hello " + principal;
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().disable();
    http.authorizeRequests()
      .antMatchers("/uaa/**", "/login").permitAll()
//      .antMatchers("/uaa/login*logout").permitAll()
      .anyRequest().authenticated()
//      .and()
//      .formLogin()
//        .loginPage("/uaa/login").permitAll()
//        .failureUrl("/uaa/login?error").permitAll()
//        .defaultSuccessUrl("/uaa/home")
//    .and()
//      .logout()
//        .logoutUrl("/uaa/logout")
//        .logoutSuccessUrl("/uaa/login?logout").permitAll()
      .and()
      .logout().permitAll()
      .logoutSuccessUrl("/login?logout")
      .and()
      .csrf().disable();
    
    
  }

  @Bean
  public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails resource,
      OAuth2ClientContext context) {
    LOGGER.trace("OAuth2ProtectedResourceDetails:" + resource);
    return new OAuth2RestTemplate(resource, context);
  }
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
