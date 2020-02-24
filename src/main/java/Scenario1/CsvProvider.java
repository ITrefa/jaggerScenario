package Scenario1;

import au.com.bytecode.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvProvider {
  //  private static final String COMMA_DELIMITER = ",";

    //    public List<List<String>> CsvProvider(String pathToFile) {
//        List<List<String>> records = new ArrayList<>();
//
//        try (
//                BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(COMMA_DELIMITER);
//                records.add(Arrays.asList(values));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return records;
//    }
//
    public List<List<String>> CsvProvider(InputStream pathToFile) {
        List<List<String>> records = new ArrayList<>();

        try (InputStreamReader inputStreamReader = new InputStreamReader(pathToFile)) {
            CSVReader csvReader = new CSVReader(inputStreamReader, ',');
            List<String[]> allRows = csvReader.readAll();
            for (String[] allRow : allRows) {
                records.add(Arrays.asList(allRow));
            }

        }

        catch (IOException ex) {
            ex.printStackTrace();
        }
         return records;

    }



    }


