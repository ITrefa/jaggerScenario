package test.Scenario2;

import com.griddynamics.jagger.coordinator.NodeContext;
import com.griddynamics.jagger.engine.e1.collector.ResponseValidator;
import com.griddynamics.jagger.engine.e1.collector.ResponseValidatorProvider;
import com.griddynamics.jagger.invoker.scenario.JHttpUserScenario;
import com.griddynamics.jagger.invoker.scenario.JHttpUserScenarioInvocationResult;
import com.griddynamics.jagger.invoker.v2.JHttpQuery;

//TODO this validator doesn't work
public class ResponseTypeValidator implements ResponseValidatorProvider {
    @Override
    public ResponseValidator<JHttpQuery, JHttpUserScenario, JHttpUserScenarioInvocationResult> provide(String sessionId, String taskId, NodeContext kernelContext) {
        return new ResponseValidator<JHttpQuery, JHttpUserScenario, JHttpUserScenarioInvocationResult>(sessionId, taskId, kernelContext) {
            @Override
            public String getName() {
                return "Successful type validator";
            }

            @Override
            public boolean validate(JHttpQuery jHttpQuery, JHttpUserScenario jHttpUserScenario, JHttpUserScenarioInvocationResult jHttpResponse, long l) {
                return jHttpResponse.getStepInvocationResults().contains("Content-Type=[application/xml]");
            }

        };
    }
}
