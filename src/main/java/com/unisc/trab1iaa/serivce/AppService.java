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

    public void training() throws Exception {
        log.info("Iniciou training set " + new Date());
        createTrainingSetFromCsv();
        log.info("Finalizou training set" + new Date());
        log.info("Iniciou treinamento " + new Date());
        RNA.backpropagation.Learn(dataSet);
        log.info("Finalizou treinamento " + new Date());
        System.out.println(testeValue);
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

    public List<String> getQuestions() {
        return Arrays.asList(
                "Se um de nós pedir desculpas quando nossa discussão se deteriora, a discussão termina.",
                "Eu sei que podemos ignorar nossas diferenças, mesmo que as coisas às vezes se tornem difíceis.",
                "Quando precisamos disso, podemos levar nossas discussões com meu cônjuge desde o início e corrigi-las.",
                "Quando eu discuto com meu cônjuge, entrar em contato com ele eventualmente funcionará.",
                "O tempo que eu passei com minha esposa é especial para nós.",
                "Não temos tempo em casa como parceiros.",
                "Somos como dois estranhos que compartilham o mesmo ambiente em casa e não na família.",
                "Eu gosto de nossas férias com minha esposa.",
                "Eu gosto de viajar com minha esposa.",
                "A maioria de nossos objetivos é comum ao meu cônjuge.",
                "Penso que um dia, no futuro, quando olho para trás, vejo que meu cônjuge e eu estivemos em harmonia um com o outro.",
                "Meu cônjuge e eu temos valores semelhantes em termos de liberdade pessoal.",
                "Meu cônjuge e eu temos um senso semelhante de entretenimento.",
                "A maioria de nossos objetivos para as pessoas (filhos, amigos, etc.) são os mesmos.",
                "Nossos sonhos com meu cônjuge são semelhantes e harmoniosos.",
                "Somos compatíveis com meu cônjuge sobre o que deve ser o amor.",
                "Compartilhamos com meu cônjuge os mesmos pontos de vista sobre ser feliz em nossa vida.",
                "Meu cônjuge e eu temos idéias similares sobre como o casamento deve ser",
                "Meu cônjuge e eu temos idéias similares sobre como devem ser os papéis no casamento",
                "Meu cônjuge e eu temos valores semelhantes em confiança.",
                "Eu sei exatamente do que minha esposa gosta.",
                "Sei como meu cônjuge quer ser atendido quando está doente.",
                "Eu conheço a comida favorita de meu cônjuge.",
                "Posso lhe dizer que tipo de estresse minha esposa está enfrentando em sua vida.",
                "Eu tenho conhecimento do mundo interior do meu cônjuge.",
                "Conheço as ansiedades básicas do meu cônjuge.",
                "Sei quais são as fontes atuais de estresse de meu cônjuge.",
                "Conheço as esperanças e desejos do meu cônjuge.",
                "Eu conheço muito bem meu cônjuge.",
                "Conheço os amigos do meu cônjuge e suas relações sociais.",
                "Sinto-me agressivo quando discuto com o meu cônjuge.",
                "Quando discuto com meu cônjuge, geralmente uso expressões como 'você sempre' ou 'você nunca'.",
                "Posso usar declarações negativas sobre a personalidade de meu cônjuge durante nossas discussões.",
                "Posso usar expressões ofensivas durante nossas discussões.",
                "Posso insultar meu cônjuge durante nossas discussões.",
                "Posso ser humilhante durante nossas discussões.",
                "Minha discussão com meu cônjuge não é calma.",
                "Eu odeio a maneira de meu cônjuge abrir um assunto.",
                "Nossas discussões muitas vezes ocorrem repentinamente.",
                "Estamos apenas começando uma discussão antes que eu saiba o que está acontecendo.",
                "Quando falo com meu cônjuge sobre algo, minha calma se rompe de repente.",
                "Quando discuto com meu cônjuge, ı só sai e eu não digo uma palavra.",
                "Na maioria das vezes fico em silêncio para acalmar um pouco o ambiente.",
                "Às vezes penso que é bom para mim sair de casa por um tempo.",
                "Eu prefiro ficar em silêncio do que discutir com meu cônjuge.",
                "Mesmo que eu esteja certo na discussão, fico em silêncio para ferir meu cônjuge.",
                "Quando discuto com meu cônjuge, fico em silêncio porque tenho medo de não conseguir controlar minha raiva.",
                "Sinto-me bem em nossas discussões.",
                "Não tenho nada a ver com aquilo de que fui acusado.",
                "Na verdade, não sou eu quem é culpado daquilo de que sou acusado.",
                "Não sou eu quem está errado sobre os problemas em casa.",
                "Eu não hesitaria em contar ao meu cônjuge sobre sua inadequação.",
                "Quando discuto, lembro ao meu cônjuge de sua inadequação.",
                "Não tenho medo de contar ao meu cônjuge sobre a sua incompetência."
        );
    }

    public double[] getResult(String answer) throws Exception {
        double[] answerDouble = getDoubleArrayFromString(answer);
        return RNA.backpropagation.Recognize(answerDouble);
    }
}
