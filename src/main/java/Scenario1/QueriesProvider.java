package Scenario1;

import Scenario1.util.PropertiesProvider;
import com.griddynamics.jagger.invoker.v2.JHttpQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueriesProvider extends JHttpQuery implements Iterable {
    CsvProvider csvProvider = new CsvProvider();

    @Override
    public Iterator iterator() {
        List<JHttpQuery> queries = new ArrayList<>();
        PropertiesProvider propertiesProvider = new PropertiesProvider();

        try {
            for (List<String> query : csvProvider.CsvProvider(propertiesProvider.getPathToCsvFile())) {
                queries.add(new JHttpQuery()
                        .path(propertiesProvider.getPathToSearchPhrase())
                        .header("Accept", "application/xml")
                        .queryParam("searchPhrase", String.valueOf(query))
                        .get()
                        .responseBodyType(String.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return queries.iterator();
    }
}


