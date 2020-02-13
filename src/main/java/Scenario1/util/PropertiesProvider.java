package Scenario1.util;


import java.io.*;
import java.util.Properties;

public class PropertiesProvider {


    public Properties getTestProperties() {
        InputStream input = getClass().getClassLoader().getResourceAsStream("test.properties");
        Properties prop = new Properties();
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public String getEndpoint() {
        return getTestProperties().getProperty("endPoint");
    }

    public String getPathToCsvFile() {
       File file = new File(getTestProperties().getProperty("pathToFile"));
       return file.getAbsolutePath();
    }

    public String getPathToSearchPhrase() {
        return getTestProperties().getProperty("pathForQuery");
    }

}


