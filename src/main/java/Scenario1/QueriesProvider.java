package Scenario1;

import com.griddynamics.jagger.invoker.v2.JHttpQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class QueriesProvider extends JHttpQuery implements Iterable {
    CsvProvider csvProvider = new CsvProvider();

    @Override
    public Iterator iterator() {
        List<JHttpQuery> queries = new ArrayList<>();
        String path = "/api/kws/v4/search";
        for (List<String> query : csvProvider.CsvProvider()) {
            queries.add(new JHttpQuery()
                    .path(path)
                    .header("Accept", "application/xml")
                    .queryParam("searchPhrase", String.valueOf(query))
                    .get()
                    .responseBodyType(String.class));
        }
        return queries.iterator();
        }
}


