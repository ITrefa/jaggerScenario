package test;

import java.util.Map;

public class InvocationResult {
    private final Map<String,Double> stepInvocationResults;
    private final String scenarioId;
    private final String scenarioDisplayName;
    private final String body;

    public InvocationResult(Map<String,Double> stepInvocationResults, String scenarioId, String scenarioDisplayName, String body) {
        this.stepInvocationResults = stepInvocationResults;
        this.scenarioId = scenarioId;
        this.scenarioDisplayName = scenarioDisplayName;
        this.body = body;
    }

    public String getScenarioId() {
        return this.scenarioId;
    }

    public String getScenarioDisplayName() {
        return this.scenarioDisplayName;
    }

    public Map<String,Double> getStepInvocationResults() {
        return this.stepInvocationResults;
    }
    public String getBody() {
        return this.body;
    }
}
