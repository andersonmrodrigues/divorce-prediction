package com.unisc.trab1iaa.controller;

import com.unisc.trab1iaa.serivce.AppService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/")
    public String index() {

        try {
            AppService appService = new AppService();
            String response = appService.run();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "works";
    }
}
