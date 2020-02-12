package Scenario1;

import com.griddynamics.jagger.user.test.configurations.JLoadScenario;
import com.griddynamics.jagger.user.test.configurations.JLoadTest;
import com.griddynamics.jagger.user.test.configurations.JParallelTestsGroup;
import com.griddynamics.jagger.user.test.configurations.JTestDefinition;
import com.griddynamics.jagger.user.test.configurations.auxiliary.Id;
import com.griddynamics.jagger.user.test.configurations.load.JLoadProfileUserGroups;
import com.griddynamics.jagger.user.test.configurations.load.JLoadProfileUsers;
import com.griddynamics.jagger.user.test.configurations.load.auxiliary.NumberOfUsers;
import com.griddynamics.jagger.user.test.configurations.loadbalancer.JLoadBalancer;
import com.griddynamics.jagger.user.test.configurations.termination.JTerminationCriteria;
import com.griddynamics.jagger.user.test.configurations.termination.JTerminationCriteriaIterations;
import com.griddynamics.jagger.user.test.configurations.termination.auxiliary.IterationsNumber;
import com.griddynamics.jagger.user.test.configurations.termination.auxiliary.MaxDurationInSeconds;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Scenario1.util.PropertiesProvider;

@Configuration
public class JHttpScenarioProvider {
    @Bean
    public JLoadScenario loadScenario() {

        JTestDefinition keyWordService4 =
                JTestDefinition.builder(Id.of("keyWordService4"), new EndpointProvider(new PropertiesProvider().getEndpoint()))
                        .withLoadBalancer(JLoadBalancer.builder(JLoadBalancer.DefaultLoadBalancer.ONE_BY_ONE).build())
                        .withQueryProvider(new QueriesProvider())
                        .addValidator(new CodeValidator())
                        .addValidator(new TypeValidator())
                        .addListener(new ListenerByteSize())
                        .addListener(new ListenerCountProduct())
                        .build();


        JLoadProfileUsers userProfile = JLoadProfileUsers.builder(NumberOfUsers.of(2)).withStartDelayInSeconds(0).withLifeTimeInSeconds(40).build();

        JTerminationCriteria jTerminationCriteria = JTerminationCriteriaIterations
                .of(IterationsNumber.of(9), MaxDurationInSeconds.of(10));

        JLoadTest jLoadTest = JLoadTest
                .builder(Id.of("load_test_1"), keyWordService4, JLoadProfileUserGroups.builder(userProfile).build(), jTerminationCriteria)
                .build();


        JParallelTestsGroup jParallelTestsGroup = JParallelTestsGroup
                .builder(Id.of("parallel_test_group_1"), jLoadTest)
                .build();


        return JLoadScenario.builder(Id.of("load_scenario_1"), jParallelTestsGroup)
                .build();
    }
}
