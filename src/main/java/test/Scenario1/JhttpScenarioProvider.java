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

import static com.griddynamics.jagger.user.test.configurations.loadbalancer.JLoadBalancer.DefaultLoadBalancer.ROUND_ROBIN;

@Configuration
public class JhttpScenarioProvider {

    @Bean
    public JLoadScenario jaggerLoadScenario1() {

        JTestDefinition keyWordService4 =
                JTestDefinition.builder(Id.of("keyWordService4"), new EndpointProvider())
                        .withQueryProvider(new QueriesProvider())
                        .addValidator(new CodeValidator())
                        .addListener(new Listener())
                        .build();

        JLoadProfile jLoadProfileRps1 = JLoadProfileRps
                .builder(RequestsPerSecond.of(2))
                .withMaxLoadThreads(1)
                .withWarmUpTimeInMilliseconds(10000)
                .build();

        JTerminationCriteria jTerminationCriteria1 = JTerminationCriteriaIterations
                .of(IterationsNumber.of(9), MaxDurationInSeconds.of(15));

        JLoadTest jLoadTest1 = JLoadTest
                .builder(Id.of("lt_1"), keyWordService4, jLoadProfileRps1, jTerminationCriteria1)
                .build();


        JParallelTestsGroup jParallelTestsGroup1 = JParallelTestsGroup
                .builder(Id.of("ptg_1"), jLoadTest1)
                .build();


        return JLoadScenario.builder(Id.of("ls_1"), jParallelTestsGroup1)
                .build();
    }
}
