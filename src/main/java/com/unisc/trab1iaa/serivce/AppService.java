package com.unisc.trab1iaa.serivce;

import ADReNA_API.Data.DataSet;
import ADReNA_API.Data.DataSetObject;
import ADReNA_API.NeuralNetwork.Backpropagation;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.unisc.trab1iaa.serivce.RNA.INPUT_SIZE;
import static com.unisc.trab1iaa.serivce.RNA.OUTPIT_SIZE;

@Slf4j
public class AppService {

    public static final String CSV_PATH = "D:\\Unisc\\IA Avançada\\divorce-prediction\\dataset\\divorce_data.csv";

    private Backpropagation backpropagation;
    private DataSet dataSet;

    private String testeValue = "";

    public AppService() {
        // O input é 62, por que cada resposta vai ter 3 bits, como são 54 perguntas então a string terá 162 caracteres
        this.dataSet = new DataSet(INPUT_SIZE, OUTPIT_SIZE);
    }

    public String run() throws Exception {
        log.info("Iniciou training set " + new Date());
        createTrainingSetFromCsv();
        log.info("Finalizou training set" + new Date());
        log.info("Iniciou treinamento " + new Date());
        RNA.backpropagation.Learn(dataSet);
        log.info("Finalizou treinamento " + new Date());
        double[] testRegnize = getDoubleArrayFromString(testeValue);
        double[] resposta = RNA.backpropagation.Recognize(testRegnize);
        return Arrays.toString(resposta);
    }

    private void createTrainingSetFromCsv() throws Exception {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var count = 0;
        for (List<String> row : records) {
            if (count != 0) {
                for (String values : row) {
                    String value = "";
                    Object[] columnValues = row.toArray();
                    String result = (String) columnValues[columnValues.length - 1];
                    for (int i = 0; i < columnValues.length - 1; i++) {
                        value += getValuesInBitsFromNumber((String) columnValues[i]);
                    }
                    if (count == 2) {
                        testeValue = value;
                    }
                    double[] results = getDoubleArrayFromString(value);
                    dataSet.Add(new DataSetObject(results, new double[]{Double.parseDouble(result)}));
                }
            }
            count++;
        }
    }

    private static double[] getDoubleArrayFromString(String value) {
        Pattern pattern = Pattern.compile("");
        return pattern.splitAsStream(value)
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    public String getValuesInBitsFromNumber(String value) {
        switch (value) {
            case "0":
                return "000"; // Nunca
            case "1":
                return "001"; // Raramente
            case "2":
                return "010"; // Médiamente
            case "3":
                return "011"; // Frequentemente
            case "4":
                return "100"; // Sempre
            default:
                return null;
        }
    }
}
