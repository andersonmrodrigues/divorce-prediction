package com.unisc.trab1iaa.controller;

import ADReNA_API.Data.DataSet;
import ADReNA_API.Data.DataSetObject;
import ADReNA_API.NeuralNetwork.Backpropagation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Arrays;

@RestController
public class AppController {

    @GetMapping("/")
    public String index() {

        try {
            Backpropagation backpropagation = new Backpropagation(2, 1);

            DataSet dataSet = new DataSet(2, 1);

            dataSet.Add(new DataSetObject(new double[]{0,0}, new double[]{0}));
            dataSet.Add(new DataSetObject(new double[]{0,1}, new double[]{1}));
            dataSet.Add(new DataSetObject(new double[]{1,0}, new double[]{1}));
            dataSet.Add(new DataSetObject(new double[]{1,1}, new double[]{0}));

            backpropagation.Learn(dataSet);

            double[] resposta = backpropagation.Recognize(new double[] {0, 1});
            return Arrays.toString(resposta);
        } catch (Exception e){

        }



        return "works";
    }
}
