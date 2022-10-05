package com.unisc.trab1iaa.controller;

import com.unisc.trab1iaa.dto.ConfigDTO;
import com.unisc.trab1iaa.serivce.AppService;
import com.unisc.trab1iaa.serivce.RNA;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/")
    public String index() {

        try {
            AppService appService = new AppService();
            RNA.setDefaultValues();
            String response = appService.run();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "works";
    }

    @PostMapping("/set-values")
    public void setValues(@RequestBody ConfigDTO configDTO) {
        RNA.setErrorRate(configDTO.getErrorRate());
        RNA.setMaxInterationNumber(configDTO.getMaxInterationNumber());
        RNA.setLearningRate(configDTO.getLearningRate());
    }

    @GetMapping("/questions")
    public void getAllQuestions(){

    }
}
