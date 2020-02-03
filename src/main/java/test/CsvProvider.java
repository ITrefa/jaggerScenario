package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvProvider {

    private static final String COMMA_DELIMITER = ",";
    private String pathToFile = "/Users/elaletina/jagger/src/main/resources/dataProvider.csv";


    public List<List<String>> CsvProvider() {
        List<List<String>> records = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

}
