package com.example.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class HelloController{

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private static RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/hello")
    public String hello(){
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        return "hello world";
    }

    @RequestMapping("/testSleuth1")
    public String test(){

        String result = restTemplate.getForObject("http://localhost:8081/testSleuth2",String.class);
        logger.info("调用请求1");
        return result;
    }

    @RequestMapping("/testSleuth2")
    public String test2(){
        String result = restTemplate.getForObject("http://localhost:8081/testSleuth3",String.class);
        logger.info("调用请求2");
        return result;
    }

    @RequestMapping("/testSleuth3")
    public String test3(){


        return "hello world";
    }







}
