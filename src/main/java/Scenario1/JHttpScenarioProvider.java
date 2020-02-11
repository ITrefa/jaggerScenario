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


@Configuration
public class JHttpScenarioProvider {
    @Bean
    public JLoadScenario loadScenario() {

        JTestDefinition keyWordService4 =
                JTestDefinition.builder(Id.of("keyWordService4"), new EndpointProvider())
                        .withLoadBalancer(JLoadBalancer.builder(JLoadBalancer.DefaultLoadBalancer.ONE_BY_ONE).build())
                        .withQueryProvider(new QueriesProvider())
                        .addValidator(new CodeValidator())
                        .addValidator(new TypeValidator())
                        .addListener(new ListenerByteSize())
                        .addListener(new ListenerCountProduct())
                        .build();


        JLoadProfileUsers u1 = JLoadProfileUsers.builder(NumberOfUsers.of(2)).withStartDelayInSeconds(0).withLifeTimeInSeconds(40).build();

        JTerminationCriteria jTerminationCriteria = JTerminationCriteriaIterations
                .of(IterationsNumber.of(9), MaxDurationInSeconds.of(10));

        JLoadTest jLoadTest1 = JLoadTest
                .builder(Id.of("lt_1"), keyWordService4, JLoadProfileUserGroups.builder(u1).build(), jTerminationCriteria)
                .build();


        JParallelTestsGroup jParallelTestsGroup = JParallelTestsGroup
                .builder(Id.of("ptg_1"), jLoadTest1)
                .build();


        return JLoadScenario.builder(Id.of("ls_1"), jParallelTestsGroup)
                .build();
    }
}
