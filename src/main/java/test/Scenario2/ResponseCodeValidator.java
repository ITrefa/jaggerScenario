package test.Scenario2;

import com.griddynamics.jagger.coordinator.NodeContext;
import com.griddynamics.jagger.engine.e1.collector.ResponseValidator;
import com.griddynamics.jagger.engine.e1.collector.ResponseValidatorProvider;
import com.griddynamics.jagger.invoker.scenario.JHttpUserScenario;
import com.griddynamics.jagger.invoker.scenario.JHttpUserScenarioInvocationResult;
import com.griddynamics.jagger.invoker.v2.JHttpQuery;

public class ResponseCodeValidator implements ResponseValidatorProvider {
        @Override
        public ResponseValidator<JHttpQuery, JHttpUserScenario, JHttpUserScenarioInvocationResult> provide(String sessionId, String taskId, NodeContext kernelContext) {
            return new ResponseValidator<JHttpQuery, JHttpUserScenario, JHttpUserScenarioInvocationResult>(sessionId, taskId, kernelContext) {
                @Override
                public String getName() {
                    return "Successful status code validator";
                }

                @Override
                public boolean validate(JHttpQuery jHttpQuery, JHttpUserScenario jHttpUserScenario, JHttpUserScenarioInvocationResult jHttpResponse, long l) {
                    return jHttpResponse.getSucceeded();
                }

            };
        }
}
