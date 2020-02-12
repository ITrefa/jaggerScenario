package Scenario1;

import com.griddynamics.jagger.invoker.v2.JHttpQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Scenario1.util.PropertiesProvider;

public class QueriesProvider extends JHttpQuery implements Iterable {
    CsvProvider csvProvider = new CsvProvider();

    @Override
    public Iterator iterator() {
        List<JHttpQuery> queries = new ArrayList<>();
        PropertiesProvider propertiesProvider = new PropertiesProvider();

        for (List<String> query : csvProvider.CsvProvider(propertiesProvider.getPathToCsvFile())) {
            queries.add(new JHttpQuery()
                    .path(propertiesProvider.getPathToSearchPhrase())
                    .header("Accept", "application/xml")
                    .queryParam("searchPhrase", String.valueOf(query))
                    .get()
                    .responseBodyType(String.class));
        }
        // String path = "/api/kws/v4/search";

        return queries.iterator();
    }
}


