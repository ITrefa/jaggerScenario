package test.Scenario1;

import com.griddynamics.jagger.user.test.configurations.JLoadScenario;
import com.griddynamics.jagger.user.test.configurations.JLoadTest;
import com.griddynamics.jagger.user.test.configurations.JParallelTestsGroup;
import com.griddynamics.jagger.user.test.configurations.JTestDefinition;
import com.griddynamics.jagger.user.test.configurations.auxiliary.Id;
import com.griddynamics.jagger.user.test.configurations.load.JLoadProfile;
import com.griddynamics.jagger.user.test.configurations.load.JLoadProfileRps;
import com.griddynamics.jagger.user.test.configurations.load.auxiliary.RequestsPerSecond;
import com.griddynamics.jagger.user.test.configurations.loadbalancer.JLoadBalancer;
import com.griddynamics.jagger.user.test.configurations.termination.JTerminationCriteria;
import com.griddynamics.jagger.user.test.configurations.termination.JTerminationCriteriaIterations;
import com.griddynamics.jagger.user.test.configurations.termination.auxiliary.IterationsNumber;
import com.griddynamics.jagger.user.test.configurations.termination.auxiliary.MaxDurationInSeconds;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.ResponseCodeValidator;

import static com.griddynamics.jagger.user.test.configurations.loadbalancer.JLoadBalancer.DefaultLoadBalancer.ROUND_ROBIN;

@Configuration
public class JhttpScenarioProvider {

    @Bean
    public JLoadScenario jaggerLoadScenario() {

        JTestDefinition keyWordService =
                JTestDefinition.builder(Id.of("keyWordService"), new EndpointProvider())
                        .withQueryProvider(new QueriesProvider())
                        .addValidator(new ResponseCodeValidator())
                        .addListener(new Listener())
                        //   .addValidator(new ResponseTypeValidator())
                        .build();

        JLoadProfile jLoadProfileRps = JLoadProfileRps
                .builder(RequestsPerSecond.of(2))
                .withMaxLoadThreads(1)
                .withWarmUpTimeInMilliseconds(10000)
                .build();

        JTerminationCriteria jTerminationCriteria = JTerminationCriteriaIterations
                .of(IterationsNumber.of(9), MaxDurationInSeconds.of(15));

        JLoadTest jLoadTest = JLoadTest
                .builder(Id.of("lt_1"), keyWordService, jLoadProfileRps, jTerminationCriteria)
                .build();

        JParallelTestsGroup jParallelTestsGroup = JParallelTestsGroup
                .builder(Id.of("ptg_1"), jLoadTest)
                .build();

        // To launch your load scenario, set 'jagger.load.scenario.id.to.execute' property's value equal to the load scenario id
        // You can do it via system properties or in the 'environment.properties' file

        return JLoadScenario.builder(Id.of("ls_1"), jParallelTestsGroup)
                .build();
    }
}
