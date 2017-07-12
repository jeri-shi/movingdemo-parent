package com.shijin.learn.movingdemo.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 
 *
 */
@EnableZuulProxy
@SpringBootApplication
public class App
{

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
