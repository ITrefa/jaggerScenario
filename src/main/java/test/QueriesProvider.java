package test;

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
        for (int i = 1; i < csvProvider.CsvProvider().size(); i++)
            queries.add(new JHttpQuery()
                    .path(path)
                    .queryParam("searchPhrase", String.valueOf(csvProvider.CsvProvider().get(i)))
                    .get()
                    .responseBodyType(String.class));
        return queries.iterator();
    }
}


