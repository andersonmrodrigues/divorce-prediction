package com.unisc.trab1iaa.controller;

import com.unisc.trab1iaa.dto.ConfigDTO;
import com.unisc.trab1iaa.serivce.AppService;
import com.unisc.trab1iaa.serivce.RNA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
public class AppController {

    private AppService appService;

    @EventListener(ApplicationReadyEvent.class)
    public void onStart() throws Exception {
        RNA.setDefaultValues();
        appService = new AppService();
        appService.training();
    }

    @GetMapping("/rna/training")
    public ResponseEntity<HttpStatus> training() {
        try {
            appService.training();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rna/get-result")
    public ResponseEntity<double[]> getAnswer(@RequestParam(name = "answer") String answer) {
        try {
            return new ResponseEntity<>(appService.getResult(answer), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/rna/set-values")
    public ResponseEntity<HttpStatus> setValues(@RequestBody ConfigDTO configDTO) {
        try {
            RNA.setErrorRate(configDTO.getErrorRate());
            RNA.setMaxInterationNumber(configDTO.getMaxInterationNumber());
            RNA.setLearningRate(configDTO.getLearningRate());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rna/questions")
    public ResponseEntity<List<String>> getAllQuestions() {
        try {
            List<String> questions = appService.getQuestions();
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
