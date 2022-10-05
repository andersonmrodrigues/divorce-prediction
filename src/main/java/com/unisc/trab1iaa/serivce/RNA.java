package com.unisc.trab1iaa.serivce;

import ADReNA_API.NeuralNetwork.Backpropagation;

public class RNA {

    public static Integer INPUT_SIZE = 162;
    public static Integer OUTPIT_SIZE = 1;

    public static Backpropagation backpropagation = new Backpropagation(INPUT_SIZE, OUTPIT_SIZE);
    public static void setDefaultValues() {
        backpropagation.SetLearningRate(0.5);
        backpropagation.SetErrorRate(0.005);
        backpropagation.SetMaxIterationNumber(500000);
    }

    public static void setLearningRate(Double value){
        backpropagation.SetLearningRate(value);
    }

    public static void setErrorRate(Double value){
        backpropagation.SetErrorRate(value);
    }

    public static void setMaxInterationNumber(Integer value){
        backpropagation.SetMaxIterationNumber(value);
    }

}
