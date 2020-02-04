package test.Scenario2;

import com.griddynamics.jagger.invoker.scenario.JHttpUserScenarioInvocationListener;
import com.griddynamics.jagger.invoker.scenario.JHttpUserScenarioInvokerProvider;
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
public class ScenarioProvider {


    @Bean
    public JLoadScenario jaggerLoadScenario2() {

        JTestDefinition keyWordService =
                JTestDefinition.builder(Id.of("keyWordService"), new SimpleScenario())
                        .withInvoker((new JHttpUserScenarioInvokerProvider()))
                        .withLoadBalancer(JLoadBalancer.builder(ROUND_ROBIN)
                                .build())
                        .addListener(JHttpUserScenarioInvocationListener.builder()
                                .withLatencyAvgStddevAggregators()
                                .withLatencyMinMaxAggregators()
                                .withLatencyPercentileAggregators(50D, 95D, 99D)
                                .build())
                        .addValidator(new ResponseCodeValidator())
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
                .builder(Id.of("lt_2"), keyWordService, jLoadProfileRps, jTerminationCriteria)
                .build();

        JParallelTestsGroup jParallelTestsGroup = JParallelTestsGroup
                .builder(Id.of("ptg_2"), jLoadTest)
                .build();

        return JLoadScenario.builder(Id.of("ls_2"), jParallelTestsGroup)
                .build();
    }


}
