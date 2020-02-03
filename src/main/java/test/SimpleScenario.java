package test;

import com.griddynamics.jagger.invoker.scenario.JHttpScenarioGlobalContext;
import com.griddynamics.jagger.invoker.scenario.JHttpUserScenario;
import com.griddynamics.jagger.invoker.scenario.JHttpUserScenarioStep;
import com.griddynamics.jagger.invoker.v2.JHttpEndpoint;
import com.griddynamics.jagger.invoker.v2.JHttpQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SimpleScenario implements Iterable {

    public static final String SCENARIO_ID = "my-user-scenario";

    private static Logger log = LoggerFactory.getLogger(SimpleScenario.class);

    private List<JHttpUserScenario> userScenarios = new ArrayList<>();
    public CsvProvider csvProvider = new CsvProvider();
    private String pathToData = "/api/kws/v4/search";
    int size;


    public SimpleScenario() {

        JHttpUserScenario userScenario = new JHttpUserScenario(SCENARIO_ID, "Scenario");
        for (int i = 0; i < csvProvider.CsvProvider().size(); i++) {
            userScenario
                    .withScenarioGlobalContext(new JHttpScenarioGlobalContext()
                            .withGlobalEndpoint(new JHttpEndpoint("http://localhost:8082/"))
                            .withGlobalHeader("Accept", "application/xml"))
                    .addStep(JHttpUserScenarioStep.builder(String.valueOf(i))
                            .withDisplayName("Step #" + i)
                            .withQuery(new JHttpQuery()
                                    .path(pathToData)
                                    .queryParam("searchPhrase", String.valueOf(csvProvider.CsvProvider().get(i)))
                                    .get()
                                    .responseBodyType(String.class))
                            .withPostProcessFunction(jHttpResponse -> {
                                size = jHttpResponse.getBody().toString().getBytes().length;
                                if (size == 0) {
                                    log.error("Response is not correct.");
                                    return false;
                                }
                                log.info("BYTE SIZE" + size);
                                return true;
                            })
                            .build());
        }

        userScenarios.add(userScenario);

    }

    @Override
    public Iterator<JHttpUserScenario> iterator() {
        return userScenarios.iterator();
    }
}
